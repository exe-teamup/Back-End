package com.team.exeteamup.service.impl;

import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.mapper.AccountMapper;
import com.team.exeteamup.mapper.StudentProfileMapper;
import com.team.exeteamup.mapper.lecturer.LecturerMapper;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.repository.LecturerRepository;
import com.team.exeteamup.repository.UserRepository;
import com.team.exeteamup.service.inter.UserProfileService;
import com.team.exeteamup.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final StudentProfileMapper studentProfileMapper;
    private final LecturerRepository lecturerRepository;
    private final LecturerMapper lecturerMapper;
    private final AccountMapper accountMapper;
    private final UserUtils userUtils;

    @Override
    public Object getProfile() {
        Account account = userUtils.getCurrentAccount();

        switch (account.getRole()) {
            case STUDENT:
                User user = userRepository.findByAccountId(account.getId())
                        .orElseThrow(() -> new AppException("Không tìm thấy hồ sơ sinh viên"));
                return studentProfileMapper.toResponse(user);

            case LECTURER:
                Lecturer lecturer = lecturerRepository.findByAccountId(account.getId())
                        .orElseThrow(() -> new AppException("Không tìm thấy hồ sơ giảng viên"));
                return lecturerMapper.toResponse(lecturer);

            case ADMIN:
            case MODERATOR:
                return accountMapper.toResponse(account);

            default:
                throw new AppException("Vai trò người dùng không được hỗ trợ để lấy hồ sơ: " + account.getRole());
        }
    }

    @Override
    //@CacheEvict(value = "user", key = "#studentId")
    public StudentProfileResponse updateStudentProfile(Long studentId, StudentProfileRequest request) {
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new AppException("Sinh viên không tồn tại"));

        Optional.ofNullable(request.getPhoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(request.getBio()).ifPresent(user::setBio);

        User updatedUser = userRepository.save(user);

        return studentProfileMapper.toResponse(updatedUser);
    }
}
