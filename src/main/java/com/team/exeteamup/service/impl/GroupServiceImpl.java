package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.request.GroupUpdateRequest;
import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.*;
import com.team.exeteamup.mapper.GroupMapper;
import com.team.exeteamup.repository.*;
import com.team.exeteamup.service.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    @Transactional
    public GroupResponse createGroup(GroupRequest groupRequest) {
        long studentId = groupRequest.getStudentId();
        Student leader = studentRepository.findById(groupRequest.getStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

        if (leader.getGroup() != null) {
            throw new RuntimeException("Sinh viên này đã thuộc nhóm khác");
        }

        Group group = Group.builder()
                .groupName(groupRequest.getGroupName())
                .groupStatus(true)
                .build();

        group = groupRepository.save(group);
        leader.setGroup(group);
        leader.setIsLeader(true);
        studentRepository.save(leader);

        List<Student> members = new ArrayList<>();
        members.add(leader);

        if (groupRequest.getMemberEmails() != null && !groupRequest.getMemberEmails().isEmpty()) {
            for (String email : groupRequest.getMemberEmails()) {
                Student member = studentRepository.findByAccount_Email(email)
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
        group.setStudents(members);
        group.setMemberCount(members.size());
        groupRepository.save(group);

        return groupMapper.toResponse(group);
    }

    @Override
    @Transactional
    public void deleteGroup(long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Nhóm không tồn tại"));

        List<Student> students = group.getStudents();
        for (Student student : students) {
            student.setGroup(null);
            student.setIsLeader(false);
        }
        studentRepository.saveAll(students);

        group.setGroupStatus(false);
        group.setStudents(new ArrayList<>());
        group.setMemberCount(0);

        groupRepository.save(group);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
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