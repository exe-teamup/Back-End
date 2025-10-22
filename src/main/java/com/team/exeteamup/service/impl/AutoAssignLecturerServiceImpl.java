package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.AutoAssignLecturerRequest;
import com.team.exeteamup.dto.response.*;
import com.team.exeteamup.entity.*;
import com.team.exeteamup.enums.CourseStatus;
import com.team.exeteamup.enums.GroupStatus;
import com.team.exeteamup.mapper.AutoAssignLecturerMapper;
import com.team.exeteamup.repository.*;
import com.team.exeteamup.service.AutoAssignLecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutoAssignLecturerServiceImpl implements AutoAssignLecturerService {

    private final GroupRepository groupRepository;
    private final GroupRegisterLecturerRepository registerLecturerRepository;
    private final LecturerRepository lecturerRepository;
    private final CourseRepository courseRepository;
    private final AutoAssignLecturerMapper mapper;

    @Override
    @Transactional
    public AutoAssignLecturerResponse autoAssignLecturers(AutoAssignLecturerRequest request) {

        List<Group> groups = groupRepository.findAll()
                .stream()
                .filter(g -> g.getLecturerSelections() != null && g.getLecturerSelections().size() == 3)
                .collect(Collectors.toList());

        List<AutoAssignGroupResponse> resultList = new ArrayList<>();
        int success = 0, fail = 0;

        for (Group group : groups) {
            boolean assigned = false;

            // Sắp xếp giảng viên theo thứ tự ưu tiên
            List<GroupRegisterLecturer> selections = group.getLecturerSelections()
                    .stream()
                    .sorted(Comparator.comparing(GroupRegisterLecturer::getRegisterOrder))
                    .toList();

            for (GroupRegisterLecturer selection : selections) {
                Lecturer lecturer = selection.getLecturer();

                // Kiểm tra giới hạn nhóm
                long count = groupRepository.countByOfficialLecturer(lecturer);
                if (count >= request.getMaxGroupsPerLecturer()) continue;

                // Gán giảng viên
                group.setOfficialLecturer(lecturer);
                group.setGroupStatus(GroupStatus.DRAFT);
                groupRepository.save(group);

                resultList.add(mapper.toResponse(group, lecturer, selection.getRegisterOrder(), true, "Phân công thành công"));
                success++;
                assigned = true;
                break;
            }

            if (!assigned) {
                resultList.add(mapper.toResponse(group, null, 0, false, "Không tìm được giảng viên phù hợp"));
                fail++;
            }
        }

        mergeGroupsIntoCourses();

        return AutoAssignLecturerResponse.builder()
                .totalGroups(groups.size())
                .successfullyAssigned(success)
                .failToAssigned(fail)
                .assignedGroups(resultList)
                .build();
    }

    private void mergeGroupsIntoCourses() {
        List<Group> assignedGroups = groupRepository.findByGroupStatus(GroupStatus.DRAFT);

        Map<Lecturer, List<Group>> groupedByLecturer = assignedGroups.stream()
                .collect(Collectors.groupingBy(Group::getOfficialLecturer));

        for (Map.Entry<Lecturer, List<Group>> entry : groupedByLecturer.entrySet()) {
            Lecturer lecturer = entry.getKey();
            List<Group> lecturerGroups = entry.getValue();

            Course existingCourse = courseRepository.findByLecturer(lecturer).orElse(null);
            if (existingCourse == null) {
                existingCourse = Course.builder()
                        .courseName(lecturer.getFullName())
                        .lecturer(lecturer)
                        .status(CourseStatus.DRAFT)
                        .build();
                courseRepository.save(existingCourse);
            }

            for (Group g : lecturerGroups) {
                g.setCourse(existingCourse);
                groupRepository.save(g);
            }
        }
    }

    /*@Override
    @Transactional
    public void confirmAssignments() {
        List<Group> draftGroups = groupRepository.findByGroupStatus(GroupStatus.DRAFT);
        for (Group g : draftGroups) {
            g.setGroupStatus(GroupStatus.ASSIGNED);
            groupRepository.save(g);

            if (g.getCourse() != null) {
                g.getCourse().setStatus(CourseStatus.ACTIVE);
                courseRepository.save(g.getCourse());
            }
        }
    }*/
}
