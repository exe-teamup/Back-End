package com.team.exeteamup.utils;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.repository.LecturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class UserUtils {

    private final LecturerRepository lecturerRepository;


    public static Account getCurrentAccount() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (Account) authentication.getPrincipal();
    }

    public static User getCurrentUser() {
        Account account = getCurrentAccount();
        return account.getUser();
    }

    public Lecturer getCurrentLecturer() {
        Account account = getCurrentAccount();

        return lecturerRepository.findByAccount_AccountId(account.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy giảng viên tương ứng với tài khoản ID: " + account.getId()
                ));
    }

}