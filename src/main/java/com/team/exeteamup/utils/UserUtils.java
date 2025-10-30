package com.team.exeteamup.utils;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public final class UserUtils {

    private final UserService userService;

    private UserUtils(UserService userService) {
        this.userService = userService;
    }

    public static Account getCurrentAccount() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (Account) authentication.getPrincipal();
    }

    public static User getCurrentUser(Account account) {
        return account.getUser();
    }
}