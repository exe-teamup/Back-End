package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.StudentProfileResponse;

public interface UserProfileService {
    StudentProfileResponse getStudentProfile(String token);
    StudentProfileResponse updateStudentProfile(Long studentId, StudentProfileRequest request);
}
