package com.team.exeteamup.utils;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class UserUtils {

    private final UserService userService;

    private UserUtils(UserService userService) {
        this.userService = userService;
    }

    public static Account getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof Account)) {

            throw new AppException("No authenticated user found in context.");
        }

        return (Account) authentication.getPrincipal();
    }

    public static User getCurrentUser(Account account) {
        return account.getUser();
    }
}