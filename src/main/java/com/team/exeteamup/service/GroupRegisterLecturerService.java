package com.team.exeteamup.service;

import com.team.exeteamup.dto.response.LecturerSelectionResponse;
import com.team.exeteamup.dto.response.group.GroupRegisterLecturerResponse;
import com.team.exeteamup.dto.response.group.LecturerPendingGroupsResponse;

import java.util.List;

public interface GroupRegisterLecturerService {
    LecturerSelectionResponse selectLecturers(Long groupId, List<Long> lecturerIds);
    LecturerPendingGroupsResponse getPendingGroups(Long lecturerId);
}
