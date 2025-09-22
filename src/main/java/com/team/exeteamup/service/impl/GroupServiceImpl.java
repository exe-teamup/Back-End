package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.entity.*;
import com.team.exeteamup.repository.GroupRepository;
import com.team.exeteamup.repository.LecturerRepository;
import com.team.exeteamup.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final LecturerRepository lecturerRepository;

    @Override
    public Group createGroup(GroupRequest groupRequest) {
        Lecturer lecturer = null;
        if (groupRequest.getLecturerId() != null) {
            Optional<Lecturer> optionalLecturer = lecturerRepository.findById(groupRequest.getLecturerId());
            lecturer = optionalLecturer.orElse(null);
        }

        Group group = Group.builder()
                .lecturer(lecturer)
                .groupName(groupRequest.getGroupName())
                .memberCount(groupRequest.getMemberCount())
                .groupStatus(true)
                .build();
        return groupRepository.save(group);
    }
}
