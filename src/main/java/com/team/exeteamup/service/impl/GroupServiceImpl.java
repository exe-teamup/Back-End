package com.team.exeteamup.service.impl;

import com.team.exeteamup.enums.GroupFilterStatus;
import com.team.exeteamup.enums.GroupStatus;
import com.team.exeteamup.enums.event.UserGroupEventType;
import com.team.exeteamup.event.user.CreateGroupEvent;
import com.team.exeteamup.event.user.UserGroupEvent;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.request.GroupUpdateRequest;
import com.team.exeteamup.dto.response.group.GroupResponse;
import com.team.exeteamup.entity.*;
import com.team.exeteamup.mapper.GroupMapper;
import com.team.exeteamup.repository.*;
import com.team.exeteamup.service.inter.GroupService;
import com.team.exeteamup.service.inter.TokenService;
import com.team.exeteamup.specification.GroupSpecification;
import com.team.exeteamup.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.HTMLDocument;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final CourseRepository courseRepository;
    private final GroupTemplateRepository groupTemplateRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserUtils userUtils;

    @Override
    @Transactional
    //@CacheEvict(cacheNames = "group", allEntries = true)
    public GroupResponse createGroup(GroupRequest groupRequest) {
        User leader = userRepository.findById(groupRequest.getStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

        if (leader.getGroup() != null) {
            throw new RuntimeException("Sinh viên này đã thuộc nhóm khác");
        }

        Course course = courseRepository.findById(groupRequest.getCourseId())
                .orElseThrow(() -> new AppException("Lớp học không tồn tại"));

        if (!course.getUsers().contains(leader)) {
            throw new AppException("Sinh viên trưởng nhóm không thuộc lớp học này");
        }

        if (course.getGroupCount() >= course.getMaxGroup()) {
            throw new AppException("Lớp học đã đạt số lượng nhóm tối đa");
        }

        GroupTemplate groupTemplate = groupTemplateRepository.findById(groupRequest.getGroupTemplateId())
                .orElseThrow(() -> new AppException("Group template không tồn tại"));

        Group group = Group.builder()
                .groupName(groupRequest.getGroupName())
                .groupStatus(GroupStatus.ACTIVE)
                .groupTemplate(groupTemplate)
                .course(course)
                .build();

        group = groupRepository.save(group);
        leader.setGroup(group);
        leader.setIsLeader(true);
        userRepository.save(leader);

        List<User> members = new ArrayList<>();
        members.add(leader);

        if (groupRequest.getMemberEmails() != null && !groupRequest.getMemberEmails().isEmpty()) {
            for (String email : groupRequest.getMemberEmails()) {
                User member = userRepository.findByAccount_Email(email)
                        .orElseThrow(() -> new AppException("Không tìm thấy sinh viên với email: " + email));

                if (member.getGroup() != null) {
                    throw new AppException("Sinh viên với email " + email + " đã ở trong một nhóm");
                }

//                if (!course.getUsers().contains(member)) {
//                    throw new AppException("Sinh viên với email " + email + " không thuộc lớp học này");
//                }

                member.setGroup(group);
                member.setIsLeader(false);
                members.add(member);
            }
        }

        if (members.size() < 3) {
            throw new AppException("Nhóm phải có ít nhất 3 thành viên");
        }

        userRepository.saveAll(members);
        group.setUsers(members);
        group.setMemberCount(members.size());
        groupRepository.save(group);

        course.setGroupCount(course.getGroupCount() + 1);
        courseRepository.save(course);

        // publish event after creating group
        CreateGroupEvent event = new CreateGroupEvent(groupRequest.getStudentId(),
                                                      groupRequest.getGroupName());
        eventPublisher.publishEvent(event);

        return groupMapper.toResponse(group);
    }

    @Override
    @Transactional
    //@CacheEvict(cacheNames = "group", allEntries = true)
    public void deleteGroup(long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Nhóm không tồn tại"));

        List<User> users = userRepository.findAllByGroup(group);
        for (User user : users) {
            user.setGroup(null);
            user.setIsLeader(false);
        }
        userRepository.saveAll(users);
        groupRepository.delete(group);
    }

    @Override
    @Transactional(readOnly = true)
    //@Cacheable("groups")
    public List<GroupResponse> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groupMapper.toResponseList(groups);
    }

    @Override
    @Transactional
    //@CacheEvict(cacheNames = "group", allEntries = true)
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
    //@Cacheable(value = "group", key = "#groupId")
    public GroupResponse getGroupById(long groupId) {
        Group group = groupRepository.findByGroupIdAndGroupStatusTrue(groupId)
                .orElseThrow(() -> new AppException("Không tìm thấy nhóm"));
        return groupMapper.toResponse(group);
    }

    @Override
    //@Cacheable(value = "group", key = "#groupId")
    public Group findGroupById(long groupId) {
        return groupRepository.findByGroupIdAndGroupStatusTrue(groupId)
                .orElseThrow(() -> new AppException("Không tìm thấy nhóm"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupResponse> getGroupsByStatus(GroupStatus groupStatus) {
        List<Group> groups = groupRepository.findGroupByStatus(groupStatus);
        return groupMapper.toResponseList(groups);
    }

    @Override
    //@Cacheable(value = "groups_by_course", key = "#courseId")
    public List<GroupResponse> getGroupsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException("Lớp không tồn tại"));

        return groupRepository.findByCourse_CourseId(courseId)
                .stream()
                .map(groupMapper::toCourseResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public GroupResponse transferLeader(Long groupId, Long newLeaderId) {
        User currentLeader = userUtils.getCurrentUser();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Không tìm thấy nhóm"));

        User newLeader = userRepository.findById(newLeaderId)
                .orElseThrow(() -> new AppException("Không tìm thấy leader mới"));

        currentLeader.setIsLeader(false);
        newLeader.setIsLeader(true);
        userRepository.save(currentLeader);
        userRepository.save(newLeader);
        return groupMapper.toResponse(group);
    }

    @Override
    @Transactional
    public GroupResponse kickMember(Long groupId, Long memberId) {
        User currentLeader = userUtils.getCurrentUser();

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Không tìm thấy nhóm"));

        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new AppException("Không tìm thấy thành viên"));

        if (member.getUserId().equals(currentLeader.getUserId())) {
            throw new AppException("Bạn không thể tự mời mình ra khỏi nhóm");
        }

        int memberCount = userRepository.countByGroup_GroupId(groupId);
        if (memberCount <= 3) {
            throw new AppException("Nhóm cần ít nhất 3 thành viên");
        }

        member.setGroup(null);
        userRepository.save(member);

        List<User> updatedMembers = userRepository.findAllByGroup(group);
        group.setMemberCount(updatedMembers.size());

        groupRepository.save(group);

        // public event
        UserGroupEvent userGroupEvent = new UserGroupEvent(
                member.getAccount().getId(),
                member.getUserCode(),
                group.getGroupName(),
                UserGroupEventType.REMOVED_FROM_GROUP);
        eventPublisher.publishEvent(userGroupEvent);

        return groupMapper.toResponse(group);
    }


    @Override
    @Transactional
    public void leaveGroup() {
        User user = userUtils.getCurrentUser();

        if(user.getGroup() == null) {
            throw new AppException("Bạn không thuộc nhóm nào");
        }

        if (Boolean.TRUE.equals(user.getIsLeader())) {
            throw new AppException("Leader không thể rời nhóm. Vui lòng chuyển quyền cho thành viên");
        }

        Group group = user.getGroup();

        int memberCount = group.getMemberCount();
        if (memberCount <= 3) throw new AppException("Nhóm cần ít nhất 3 thành viên");

        user.setGroup(null);
        userRepository.save(user);

        UserGroupEvent userGroupEvent = new UserGroupEvent(
                user.getAccount().getId(),
                user.getUserCode(),
                user.getGroup().getGroupName(),
                UserGroupEventType.LEAVE_GROUP);
        eventPublisher.publishEvent(userGroupEvent);
    }

    @Override
    @Transactional
    public GroupResponse addMember(Long groupId, Long memberId) {
        Account account = userUtils.getCurrentAccount();
        User leader = userRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new AppException("Không tìm thấy người dùng"));

        if (!leader.getIsLeader()) {
            throw new AppException("Chỉ leader mới có quyền thêm thành viên");
        }

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Nhóm không tồn tại"));

        if (group.getGroupStatus() == GroupStatus.LOCKED) {
            throw new AppException("Nhóm đã bị khóa, không thể thêm thành viên");
        }

        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên"));

        if (member.getGroup() != null) {
            throw new AppException("Thành viên này đã thuộc nhóm khác");
        }

        int memberCount = userRepository.countByGroup_GroupId(groupId);
        if (memberCount >= 6) throw new AppException("Nhóm đã đạt tối đa 6 thành viên");

        member.setGroup(group);
        userRepository.save(member);

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
        };

        return groupMapper.toResponseList(groups);
    }

    @Override
    //@Cacheable(value = "groups_by_lecturer", key = "#lecturerId")
    public List<GroupResponse> getGroupsByLecturer(long lecturerId) {

        List<Group> groups = groupRepository.findAll();
        List<Group> filteredGroups = new ArrayList<>();
        for (Group group : groups) {
            if (group.getCourse().getLecturer().getLecturerId().equals(lecturerId)) {
                filteredGroups.add(group);
            }
        }
        return groupMapper.toResponseList(filteredGroups);
    }

    @Override
    public List<GroupResponse> getGroupsWithFilter(GroupStatus status, Long majorId) {

        Specification<Group> specification = GroupSpecification.filterGroups(status, majorId);
        List<Group> groups = groupRepository.findAll(specification);
        return groups.stream()
                .map(groupMapper::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}