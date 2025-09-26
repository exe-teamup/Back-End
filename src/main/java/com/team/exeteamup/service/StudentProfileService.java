package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.entity.Student;

public interface StudentProfileService{
    StudentProfileResponse getStudentProfile(String token);
    StudentProfileResponse updateStudentProfile(Long studentId, StudentProfileRequest request);
}
