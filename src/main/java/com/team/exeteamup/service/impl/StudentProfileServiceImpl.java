package com.team.exeteamup.service.impl;

import com.team.exeteamup.Exception.AppException;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.Student;
import com.team.exeteamup.repository.StudentRepository;
import com.team.exeteamup.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .isLeader(student.isLeader())
                .studentStatus(student.isStudentStatus())
                .groupId(student.getGroup() != null ? student.getGroup().getGroupId() : null)
                .build();
    }
}
