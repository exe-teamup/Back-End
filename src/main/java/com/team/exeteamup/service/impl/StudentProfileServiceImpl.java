package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.User;
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

        User user = studentRepository.findByAccount_AccountId(account.getAccountId())
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên"));

        return StudentProfileResponse.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getAccount().getEmail())
                .phoneNumber(user.getPhoneNumber())
                .bio(user.getBio())
                .createdAt(user.getCreatedAt())
                .isLeader(user.getIsLeader())
                .studentStatus(user.getUserStatus() != null ? user.getUserStatus().name() : null)
                .groupId(user.getGroup() != null ? user.getGroup().getGroupId() : null)
                .build();
    }

    @Override
    public StudentProfileResponse updateStudentProfile(Long studentId, StudentProfileRequest request) {
        User user = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException("Sinh viên không tồn tại"));

        Optional.ofNullable(request.getPhoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(request.getBio()).ifPresent(user::setBio);

        User updatedUser = studentRepository.save(user);

        return StudentProfileResponse.builder()
                .userId(updatedUser.getUserId())
                .fullName(updatedUser.getFullName())
                .email(updatedUser.getAccount().getEmail())
                .phoneNumber(updatedUser.getPhoneNumber())
                .bio(updatedUser.getBio())
                .createdAt(updatedUser.getCreatedAt())
                .isLeader(updatedUser.getIsLeader())
                .studentStatus(updatedUser.getUserStatus() != null ? user.getUserStatus().name() : null)
                .groupId(updatedUser.getGroup() != null ? updatedUser.getGroup().getGroupId() : null)
                .build();
    }
}
