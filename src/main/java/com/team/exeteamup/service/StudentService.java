package com.team.exeteamup.service;

import com.team.exeteamup.dto.response.StudentResponse;
import com.team.exeteamup.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    List<StudentResponse> getAllStudents();
    Page<StudentResponse> getAllStudents(Pageable pageable);
    List<StudentResponse> importStudentsFromExcel(MultipartFile file) throws IOException;
    void importStudentsNotEligible(MultipartFile file) throws IOException;
    void deleteStudentById(long studentId);
    Student findById(long studentId);
}
