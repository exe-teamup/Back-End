package com.team.exeteamup.service;

import com.team.exeteamup.dto.response.StudentProfileResponse;

public interface StudentProfileService{
    public StudentProfileResponse getStudentProfile(String token);
}
