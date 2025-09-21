package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.LoginResponse;
import com.team.exeteamup.entity.Student;

public class StudentMapper {
    public static LoginResponse toLoginResponse(Student student, String token) {
        if (student == null) return null;

        return LoginResponse.builder()
                .studentId(student.getStudentId())
                .studentCode(student.getStudentCode())
                .fullName(student.getFullName())
                .email(student.getEmail())
                .createdAt(student.getCreatedAt())
                .status(student.isStatus())
                .token(token)
                .build();
    }
}
