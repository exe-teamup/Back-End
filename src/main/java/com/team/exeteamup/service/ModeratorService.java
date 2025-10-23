package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.AssignLecturerRequest;
import com.team.exeteamup.dto.response.AssignLecturerResponse;

public interface ModeratorService {
    AssignLecturerResponse assignLecturer(AssignLecturerRequest assignLecturerRequest);
    AssignLecturerResponse updateAssignedLecturer(AssignLecturerRequest assignLecturerRequest);
}
