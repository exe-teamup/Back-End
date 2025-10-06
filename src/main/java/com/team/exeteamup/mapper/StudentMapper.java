package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.StudentResponse;
import com.team.exeteamup.entity.Student;
import com.team.exeteamup.enums.StudentStatus;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentResponse toResponse(Student student) {
        if (student == null) {
            return null;
        }

        StudentResponse response = new StudentResponse();

        response.setStudentId(student.getStudentId());
        response.setFullName(student.getFullName());
        response.setStudentCode(student.getStudentCode());
        response.setPhoneNumber(student.getPhoneNumber());
        response.setBio(student.getBio());
        response.setCreatedAt(student.getCreatedAt());

        if (student.getAccount() != null) {
            response.setEmail(student.getAccount().getEmail());
        }

        if (student.getGroup() != null) {
            response.setGroupId(student.getGroup().getGroupId());
            response.setGroupName(student.getGroup().getGroupName());
        }

        if (student.getMajor() != null) {
            response.setMajorId(student.getMajor().getMajorId());
            response.setMajorName(student.getMajor().getMajorName());
        }

        response.setStudentStatus(
                student.getStudentStatus() != null ? StudentStatus.valueOf(student.getStudentStatus().name()) : null
        );

        response.setIsLeader(student.getIsLeader() != null ? student.getIsLeader() : false);

        return response;
    }

}