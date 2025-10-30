package com.team.exeteamup.service;

import com.team.exeteamup.dto.response.UserResponse;
import com.team.exeteamup.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserResponse> getAllStudents();
    Page<UserResponse> getAllStudents(Pageable pageable);
    List<UserResponse> importStudentsFromExcel(MultipartFile file) throws IOException;
    void importStudentsNotEligible(MultipartFile file) throws IOException;
    void deleteStudentById(long studentId);
    User findById(long studentId);
    List<UserResponse> getStudentWithoutGroup();
    UserResponse getStudentById(long studentId);
    List<UserResponse> searchStudents(String keyword);
    UserResponse moveStudentCourses(Long newCourseId);
    List<UserResponse> getStudentByCourseId(long courseId);
}
