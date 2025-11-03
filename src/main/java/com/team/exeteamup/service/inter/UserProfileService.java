package com.team.exeteamup.service.inter;

import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.StudentProfileResponse;

public interface UserProfileService {
    Object getProfile(String token);
    StudentProfileResponse updateStudentProfile(Long studentId, StudentProfileRequest request);
}
