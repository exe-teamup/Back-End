package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.*;
import com.team.exeteamup.enums.AccountRole;
import com.team.exeteamup.mapper.GroupMapper;
import com.team.exeteamup.repository.*;
import com.team.exeteamup.service.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Security;
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
                .leader(leader)
                .groupStatus(true)
                .build();

        group = groupRepository.save(group);
        leader.setGroup(group);
        leader.setLeader(true);
        studentRepository.save(leader);

        List<Student> members = new ArrayList<>();
        members.add(leader);

        if (groupRequest.getMemberEmails() != null && !groupRequest.getMemberEmails().isEmpty()) {
            for (String email : groupRequest.getMemberEmails()) {
                Student member = studentRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với email: " + email));
                if (member.getGroup() != null) {
                    throw new RuntimeException("Sinh viên với email " + email + " đã ở trong một nhóm");
                }
                member.setGroup(group);
                member.setLeader(false);
                members.add(member);
            }
        }

        if(members.size() < 3) {
            throw new RuntimeException("Nhóm phải có ít nhất 3 thành viên");
        }

        studentRepository.saveAll(members);
        group.setStudents(members);
        group.setMemberCount(members.size());
        groupRepository.save(group);

        return groupMapper.toResponse(group);
    }

    @Override
    public void deleteGroup(long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
        groupRepository.delete(group);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
