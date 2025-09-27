package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.StudentResponse;
import com.team.exeteamup.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentResponse toResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setStudentId(student.getStudentId());
        response.setFullName(student.getFullName());
        response.setEmail(student.getAccount().getEmail());
        response.setPhoneNumber(student.getPhoneNumber());
        response.setStudentCode(student.getStudentCode());
        response.setStudentStatus(student.isStudentStatus());
        response.setCreatedAt(student.getCreatedAt());
        if (student.getGroup() != null) {
            response.setGroupId(student.getGroup().getGroupId());
            response.setGroupName(student.getGroup().getGroupName());
        }
        response.setIsLeader(student.isLeader());
        return response;
    }
}