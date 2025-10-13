package com.team.exeteamup.service;

import com.team.exeteamup.dto.response.LecturerSelectionResponse;

import java.util.List;

public interface GroupRegisterLecturerService {
    LecturerSelectionResponse selectLecturers(Long groupId, List<Long> lecturerIds);
}
