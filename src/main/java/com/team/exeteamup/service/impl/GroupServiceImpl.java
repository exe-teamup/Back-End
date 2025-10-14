package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.request.GroupUpdateRequest;
import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.*;
import com.team.exeteamup.mapper.GroupMapper;
import com.team.exeteamup.repository.*;
import com.team.exeteamup.service.GroupService;
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
                .groupStatus(true)
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
}