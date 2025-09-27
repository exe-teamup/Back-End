package com.team.exeteamup.service;

import com.team.exeteamup.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAllStudents();
}
