package com.team.exeteamup.service.impl;

import com.team.exeteamup.enums.GroupFilterStatus;
import com.team.exeteamup.enums.GroupStatus;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.request.GroupUpdateRequest;
import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.*;
import com.team.exeteamup.mapper.GroupMapper;
import com.team.exeteamup.repository.*;
import com.team.exeteamup.service.GroupService;
import com.team.exeteamup.service.NotificationService;
import com.team.exeteamup.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final CourseRepository courseRepository;
    private final TokenService tokenService;
    private final GroupRegisterLecturerRepository groupRegisterLecturerRepository;
    private final GroupLecturerRepository groupLecturerRepository;

    @Override
    @Transactional
    public GroupResponse createGroup(GroupRequest groupRequest) {
        User leader = studentRepository.findById(groupRequest.getStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

        if (leader.getGroup() != null) {
            throw new RuntimeException("Sinh viên này đã thuộc nhóm khác");
        }

        Course course = courseRepository.findById(groupRequest.getCourseId())
                .orElseThrow(() -> new AppException("Lớp học không tồn tại"));

        Group group = Group.builder()
                .groupName(groupRequest.getGroupName())
                .groupStatus(GroupStatus.ACTIVE)
                .course(course)
                .build();

        group = groupRepository.save(group);
        leader.setGroup(group);
        leader.setIsLeader(true);
        studentRepository.save(leader);

        List<User> members = new ArrayList<>();
        members.add(leader);

        if (groupRequest.getMemberEmails() != null && !groupRequest.getMemberEmails().isEmpty()) {
            for (String email : groupRequest.getMemberEmails()) {
                User member = studentRepository.findByAccount_Email(email)
                        .orElseThrow(() -> new AppException("Không tìm thấy sinh viên với email: " + email));
                if (member.getGroup() != null) {
                    throw new AppException("Sinh viên với email " + email + " đã ở trong một nhóm");
                }
                member.setGroup(group);
                member.setIsLeader(false);
                members.add(member);
            }
        }

        if (members.size() < 3) {
            throw new AppException("Nhóm phải có ít nhất 3 thành viên");
        }

        studentRepository.saveAll(members);
        group.setUsers(members);
        group.setMemberCount(members.size());
        groupRepository.save(group);

        return groupMapper.toResponse(group);
    }

    @Override
    @Transactional
    public void deleteGroup(long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Nhóm không tồn tại"));

        groupRegisterLecturerRepository.deleteAllByGroup(group);
        groupLecturerRepository.deleteAllByGroup(group);

        List<User> users = studentRepository.findAllByGroup(group);
        for (User user : users) {
            user.setGroup(null);
            user.setIsLeader(false);
        }
        studentRepository.saveAll(users);
        groupRepository.delete(group);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupResponse> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groupMapper.toResponseList(groups);
    }

    @Override
    @Transactional
    public GroupResponse updateGroup(long groupId, GroupUpdateRequest request) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Nhóm không tồn tại"));

        if (request.getGroupName() != null) {
            group.setGroupName(request.getGroupName());
        }

        if (request.getGroupStatus() != null) {
            group.setGroupStatus(request.getGroupStatus());
        }

        groupRepository.save(group);
        return groupMapper.toResponse(group);
    }

    @Override
    @Transactional
    public GroupResponse getGroupById(long groupId) {
        Group group = groupRepository.findByGroupIdAndGroupStatusTrue(groupId)
                .orElseThrow(() -> new AppException("Không tìm thấy nhóm"));
        return groupMapper.toResponse(group);
    }

    @Override
    public Group findGroupById(long groupId) {
        return groupRepository.findByGroupIdAndGroupStatusTrue(groupId)
                .orElseThrow(() -> new AppException("Không tìm thấy nhóm"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupResponse> getGroupsByStatus(String groupStatus) {
        GroupStatus status = GroupStatus.valueOf(groupStatus.toUpperCase());
        List<Group> groups = groupRepository.findGroupByStatus(status);
        return groupMapper.toResponseList(groups);
    }

    @Override
    public List<GroupResponse> getGroupsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException("Lớp không tồn tại"));

        return groupRepository.findByCourse_CourseId(courseId)
                .stream()
                .map(groupMapper::toCourseResponse)
                .toList();
    }

    @Override
    @Transactional
    public GroupResponse transferLeader(Long groupId, Long newLeaderId, String token) {
        Account account = tokenService.getAccountByToken(token);
        User currentLeader = studentRepository.findByAccount_AccountId(account.getAccountId())
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Không tìm thấy nhóm"));

        User newLeader = studentRepository.findById(newLeaderId)
                .orElseThrow(() -> new AppException("Không tìm thấy leader mới"));

        currentLeader.setIsLeader(false);
        newLeader.setIsLeader(true);
        studentRepository.save(currentLeader);
        studentRepository.save(newLeader);
        return groupMapper.toResponse(group);
    }

    @Override
    @Transactional
    public GroupResponse kickMember(Long groupId, Long memberId, String token) {
        Account account = tokenService.getAccountByToken(token);
        User currentLeader = studentRepository.findByAccount_AccountId(account.getAccountId())
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Không tìm thấy nhóm"));

        User member = studentRepository.findById(memberId)
                .orElseThrow(() -> new AppException("Không tìm thấy thành viên"));

        if (member.getUserId().equals(currentLeader.getUserId())) {
            throw new AppException("Bạn không thể tự mời mình ra khỏi nhóm");
        }

        int memberCount = studentRepository.countByGroup_GroupId(groupId);
        if (memberCount <= 3) throw new AppException("Nhóm cần ít nhất 3 thành viên");

        member.setGroup(null);
        studentRepository.save(member);

        List<User> updatedMembers = studentRepository.findAllByGroup(group);
        int members = updatedMembers.size();
        group.setMemberCount(members);

        GroupResponse response = groupMapper.toResponse(group);
        response.setMemberIds(updatedMembers.stream().map(User::getUserId).toList());
        response.setMemberCount(memberCount);
        return groupMapper.toResponse(group);
    }

    @Override
    @Transactional
    public void leaveGroup(Long groupId, String token) {
        Account account = tokenService.getAccountByToken(token);
        User user = studentRepository.findByAccount_AccountId(account.getAccountId())
                .orElseThrow(() -> new AppException("Không tìm thấy người dùng"));

        if (Boolean.TRUE.equals(user.getIsLeader())) {
            throw new AppException("Leader không thể rời nhóm. Vui lòng chuyển quyền cho thành viên");
        }

        int memberCount = studentRepository.countByGroup_GroupId(groupId);
        if (memberCount <= 3) throw new AppException("Nhóm cần ít nhất 3 thành viên");

        user.setGroup(null);
        studentRepository.save(user);
    }

    @Override
    @Transactional
    public GroupResponse addMember(Long groupId, Long memberId, String token) {
        Account account = tokenService.getAccountByToken(token);
        User leader = studentRepository.findByAccount_AccountId(account.getAccountId())
                .orElseThrow(() -> new AppException("Không tìm thấy người dùng"));

        if (!leader.getIsLeader()) {
            throw new AppException("Chỉ leader mới có quyền thêm thành viên");
        }

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Nhóm không tồn tại"));

        User member = studentRepository.findById(memberId)
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên"));

        if (member.getGroup() != null) {
            throw new AppException("Thành viên này đã thuộc nhóm khác");
        }

        int memberCount = studentRepository.countByGroup_GroupId(groupId);
        if (memberCount >= 6) throw new AppException("Nhóm đã đạt tối đa 6 thành viên");

        member.setGroup(group);
        studentRepository.save(member);

        Group updatedGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Không tìm thấy nhóm sau khi thêm"));
        return groupMapper.toResponse(updatedGroup);
    }

    @Override
    @Transactional
    public List<GroupResponse> filterGroups(GroupFilterStatus status) {
        List<Group> groups = switch (status) {
            case FULL_MEMBER -> groupRepository.findFullGroups();
            case LACK_MEMBER -> groupRepository.findNotFullGroups();
            case HAS_LECTURER -> groupRepository.findGroupsWithLecturerSelection();
            case NO_LECTURER -> groupRepository.findGroupsWithoutLecturerSelection();
        };

        return groupMapper.toResponseList(groups);
    }
}