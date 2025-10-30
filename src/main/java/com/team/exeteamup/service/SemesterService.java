package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.SemesterRequest;
import com.team.exeteamup.dto.response.SemesterResponse;
import com.team.exeteamup.entity.Semester;

import java.util.List;

public interface SemesterService {
    SemesterResponse createSemester(SemesterRequest semesterRequest);
    List<SemesterResponse> getAllSemesters();
    SemesterResponse getSemesterById(Long semesterId);
    SemesterResponse updateSemester(Long semesterId, SemesterRequest semesterRequest);
    void deleteSemester(Long semesterId);
    List<SemesterResponse> getSemestersOfCurrentLecturer();
}
