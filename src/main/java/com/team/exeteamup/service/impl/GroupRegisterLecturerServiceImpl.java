package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.response.LecturerSelectionResponse;
import com.team.exeteamup.dto.response.group.GroupRegisterLecturerResponse;
import com.team.exeteamup.dto.response.group.LecturerPendingGroupsResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.GroupRegisterLecturer;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.embedded.GroupLecturerId;
import com.team.exeteamup.enums.RegisterStatus;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.mapper.GroupMapper;
import com.team.exeteamup.mapper.GroupRegisterLecturerMapper;
import com.team.exeteamup.repository.GroupRegisterLecturerRepository;
import com.team.exeteamup.repository.GroupRepository;
import com.team.exeteamup.repository.LecturerRepository;
import com.team.exeteamup.service.GroupRegisterLecturerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupRegisterLecturerServiceImpl implements GroupRegisterLecturerService {

    private final GroupRegisterLecturerRepository groupRegisterLecturerRepository;
    private final GroupRepository groupRepository;
    private final LecturerRepository lecturerRepository;
    private final GroupRegisterLecturerMapper groupRegisterLecturerMapper;
    private final GroupMapper groupMapper;

    @Override
    public LecturerSelectionResponse selectLecturers(Long groupId, List<Long> lecturerIds) {
        if (lecturerIds == null || lecturerIds.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng ít nhất 1 giảng viên");
        }

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException("Nhóm không tồn tại"));

        List<GroupRegisterLecturer> selections = new ArrayList<>();

        for (int i = 0; i < lecturerIds.size(); i++) {
            Long lecturerId = lecturerIds.get(i);
            Lecturer lecturer = lecturerRepository.findById(lecturerId)
                    .orElseThrow(() -> new IllegalArgumentException("Giảng viên không tồn tại"));
            if (groupRegisterLecturerRepository.existsByGroup_GroupIdAndLecturer_LecturerId(groupId, lecturerId)) {
                throw new IllegalArgumentException("Giảng viên này đã được chọn trước đó");
            }

            GroupRegisterLecturer entity = GroupRegisterLecturer.builder()
                    .groupLecturerId(new GroupLecturerId(groupId, lecturerId))
                    .group(group)
                    .lecturer(lecturer)
                    .registerOrder(i + 1)
                    .registerStatus(RegisterStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();
            selections.add(entity);
        }
        groupRegisterLecturerRepository.saveAll(selections);
        return groupRegisterLecturerMapper.toResponse(groupId, selections);
    }

    @Override
    public LecturerPendingGroupsResponse getPendingGroups(Long lecturerId) {
        List<GroupRegisterLecturer> pendingList = groupRegisterLecturerRepository
                .findPendingGroupsByLecturerId(lecturerId, RegisterStatus.PENDING);

        List<GroupRegisterLecturerResponse> groups = pendingList.stream()
                .map(reg -> groupMapper.toGroupRegisterLecturerResponse(reg.getGroup()))
                .collect(Collectors.toList());

        return LecturerPendingGroupsResponse.builder()
                .lecturerId(lecturerId)
                .totalGroups(groups.size())
                .groups(groups)
                .build();
    }
}
