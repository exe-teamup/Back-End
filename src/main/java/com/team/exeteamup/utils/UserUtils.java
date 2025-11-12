package com.team.exeteamup.utils;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.repository.LecturerRepository;
import com.team.exeteamup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils implements ApplicationContextAware {

    private final LecturerRepository lecturerRepository;
    private final UserRepository userRepository;
    private static AccountRepository accountRepository;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        accountRepository = applicationContext.getBean(AccountRepository.class);
    }

    public Account getCurrentAccount() {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findByEmail(mail).get();
    }


    public Lecturer getCurrentLecturer() {
        Account account = getCurrentAccount();

        return lecturerRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy giảng viên tương ứng với tài khoản ID: " + account.getId()
                ));
    }


    public User getCurrentUser() {
        Account account = getCurrentAccount();

        return userRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy sinh viên tương ứng với tài khoản ID: " + account.getId()
                ));
    }

}