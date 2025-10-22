package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.AutoAssignLecturerRequest;
import com.team.exeteamup.dto.response.AutoAssignLecturerResponse;

public interface AutoAssignLecturerService {
    AutoAssignLecturerResponse autoAssignLecturers(AutoAssignLecturerRequest request);
//    void confirmAssignments();
}
