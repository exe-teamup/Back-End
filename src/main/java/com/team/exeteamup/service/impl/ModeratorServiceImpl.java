package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.AssignLecturerRequest;
import com.team.exeteamup.dto.response.AssignLecturerResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.GroupLecturer;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.embedded.GroupLecturerId;
import com.team.exeteamup.enums.LecturerStatus;
import com.team.exeteamup.enums.RegisterStatus;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.mapper.LecturerAssignmentMapper;
import com.team.exeteamup.repository.GroupLecturerRepository;
import com.team.exeteamup.repository.GroupRepository;
import com.team.exeteamup.repository.LecturerRepository;
import com.team.exeteamup.service.ModeratorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ModeratorServiceImpl implements ModeratorService {
    private final GroupRepository groupRepository;
    private final LecturerRepository lecturerRepository;
    private final GroupLecturerRepository groupLecturerRepository;
    private final LecturerAssignmentMapper lecturerAssignmentMapper;

    @Override
    public AssignLecturerResponse assignLecturer(AssignLecturerRequest request) {
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new AppException("Nhóm không tồn tại"));
        Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                .orElseThrow(() -> new AppException("Giảng viên không tồn tại"));

        GroupLecturerId id = new GroupLecturerId(group.getGroupId(), lecturer.getLecturerId());
        GroupLecturer groupLecturer = GroupLecturer.builder()
                .id(id)
                .group(group)
                .lecturer(lecturer)
                .status(LecturerStatus.ACTIVE)
                .isMain(true)
                .assignedAt(LocalDateTime.now())
                .build();
        groupLecturerRepository.save(groupLecturer);

        groupRepository.save(group);
        return lecturerAssignmentMapper.toResponse(groupLecturer);
    }

    @Override
    public AssignLecturerResponse updateAssignedLecturer(AssignLecturerRequest assignLecturerRequest) {
        Group group = groupRepository.findById(assignLecturerRequest.getGroupId())
                .orElseThrow(() -> new AppException("Nhóm không tồn tại"));
        Lecturer lecturer = lecturerRepository.findById(assignLecturerRequest.getLecturerId())
                .orElseThrow(() -> new AppException("Giảng viên không tồn tại"));

        groupLecturerRepository.deleteAllByGroup_GroupId(group.getGroupId());

        GroupLecturer newMainLecturer = GroupLecturer.builder()
                .id(new GroupLecturerId(group.getGroupId(), lecturer.getLecturerId()))
                .group(group)
                .lecturer(lecturer)
                .status(LecturerStatus.ACTIVE)
                .isMain(true)
                .assignedAt(LocalDateTime.now())
                .build();
        groupLecturerRepository.save(newMainLecturer);

        groupRepository.save(group);
        return lecturerAssignmentMapper.toResponse(newMainLecturer);
    }

}
