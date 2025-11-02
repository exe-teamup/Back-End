package com.team.exeteamup.service.inter;

import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.AdminProfileResponse;
import com.team.exeteamup.dto.response.LecturerProfileResponse;
import com.team.exeteamup.dto.response.ModeratorProfileResponse;
import com.team.exeteamup.dto.response.StudentProfileResponse;

public interface UserProfileService {
    StudentProfileResponse getStudentProfile(String token);
    StudentProfileResponse updateStudentProfile(Long studentId, StudentProfileRequest request);
    AdminProfileResponse getAdminProfile(String token);
    ModeratorProfileResponse getModeratorProfile(String token);
    LecturerProfileResponse getLecturerProfile(String token);
}
