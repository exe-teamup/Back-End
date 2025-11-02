package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.AdminProfileResponse;
import com.team.exeteamup.dto.response.LecturerProfileResponse;
import com.team.exeteamup.dto.response.ModeratorProfileResponse;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.account.AccountRole;
import com.team.exeteamup.mapper.AdminProfileMapper;
import com.team.exeteamup.mapper.LecturerProfileMapper;
import com.team.exeteamup.mapper.ModeratorProfileMapper;
import com.team.exeteamup.mapper.StudentProfileMapper;
import com.team.exeteamup.repository.LecturerRepository;
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
    private final LecturerRepository lecturerRepository;
    private final StudentProfileMapper studentProfileMapper;
    private final AdminProfileMapper adminProfileMapper;
    private final ModeratorProfileMapper moderatorProfileMapper;
    private final LecturerProfileMapper lecturerProfileMapper;

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

    @Override
    public AdminProfileResponse getAdminProfile(String token) {
        Account account = tokenService.getAccountByToken(token);

        if (account.getRole() != AccountRole.ADMIN) {
            throw new AppException("Tài khoản không phải là Admin");
        }

        return adminProfileMapper.toResponse(account);
    }

    @Override
    public ModeratorProfileResponse getModeratorProfile(String token) {
        Account account = tokenService.getAccountByToken(token);

        if (account.getRole() != AccountRole.MODERATOR) {
            throw new AppException("Tài khoản không phải là Moderator");
        }

        return moderatorProfileMapper.toResponse(account);
    }

    @Override
    public LecturerProfileResponse getLecturerProfile(String token) {
        Account account = tokenService.getAccountByToken(token);

        if (account.getRole() != AccountRole.LECTURER) {
            throw new AppException("Tài khoản không phải là Lecturer");
        }

        Lecturer lecturer = lecturerRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new AppException("Không tìm thấy thông tin giảng viên"));

        return lecturerProfileMapper.toResponse(lecturer);
    }
}
