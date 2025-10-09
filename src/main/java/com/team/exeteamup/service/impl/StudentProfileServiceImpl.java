package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.Student;
import com.team.exeteamup.repository.StudentRepository;
import com.team.exeteamup.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl implements StudentProfileService {

    private final TokenServiceImpl tokenService;


    private final StudentRepository studentRepository;

    @Override
    public StudentProfileResponse getStudentProfile(String token) {
        Account account = tokenService.getAccountByToken(token);

        Student student = studentRepository.findByAccount_AccountId(account.getAccountId())
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên"));

        return StudentProfileResponse.builder()
                .studentId(student.getStudentId())
                .fullName(student.getFullName())
                .email(student.getAccount().getEmail())
                .phoneNumber(student.getPhoneNumber())
                .bio(student.getBio())
                .createdAt(student.getCreatedAt())
                .isLeader(student.getIsLeader())
                .studentStatus(student.getStudentStatus() != null ? student.getStudentStatus().name() : null)
                .groupId(student.getGroup() != null ? student.getGroup().getGroupId() : null)
                .build();
    }

    @Override
    public StudentProfileResponse updateStudentProfile(Long studentId, StudentProfileRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException("Sinh viên không tồn tại"));

        Optional.ofNullable(request.getPhoneNumber()).ifPresent(student::setPhoneNumber);
        Optional.ofNullable(request.getBio()).ifPresent(student::setBio);

        Student updatedStudent = studentRepository.save(student);

        return StudentProfileResponse.builder()
                .studentId(updatedStudent.getStudentId())
                .fullName(updatedStudent.getFullName())
                .email(updatedStudent.getAccount().getEmail())
                .phoneNumber(updatedStudent.getPhoneNumber())
                .bio(updatedStudent.getBio())
                .createdAt(updatedStudent.getCreatedAt())
                .isLeader(updatedStudent.getIsLeader())
                .studentStatus(updatedStudent.getStudentStatus() != null ? student.getStudentStatus().name() : null)
                .groupId(updatedStudent.getGroup() != null ? updatedStudent.getGroup().getGroupId() : null)
                .build();
    }
}
