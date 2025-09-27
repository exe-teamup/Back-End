package com.team.exeteamup.service;

import com.team.exeteamup.dto.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAllStudents();
    Page<StudentResponse> getAllStudents(Pageable pageable);
}
