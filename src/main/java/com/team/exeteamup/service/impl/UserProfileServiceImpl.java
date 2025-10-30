package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.mapper.StudentProfileMapper;
import com.team.exeteamup.repository.StudentRepository;
import com.team.exeteamup.service.inter.TokenService;
import com.team.exeteamup.service.inter.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final TokenService tokenService;
    private final StudentRepository studentRepository;
    private final StudentProfileMapper studentProfileMapper;

    @Override
    public StudentProfileResponse getStudentProfile(String token) {
        Account account = tokenService.getAccountByToken(token);

        User user = studentRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên"));

        return studentProfileMapper.toResponse(user);
    }

    @Override
    public StudentProfileResponse updateStudentProfile(Long studentId, StudentProfileRequest request) {
        User user = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException("Sinh viên không tồn tại"));

        Optional.ofNullable(request.getPhoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(request.getBio()).ifPresent(user::setBio);

        User updatedUser = studentRepository.save(user);

        return studentProfileMapper.toResponse(updatedUser);
    }
}
