package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.AccountRequest;
import com.team.exeteamup.dto.response.AccountResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.mapper.AccountMapper;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.service.inter.AccountService;
import com.team.exeteamup.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserService userService;

    @Override
    @Transactional
    public Account createAccount(AccountRequest email) {
        return null;
    }

    @Override
    @Transactional
    public AccountResponse loginWithEmail(String email) {
        return null;
    }
//
//    @Override
//    public AccountResponse loginWithEmail(String email) {
//        Account newAccount = accountRepository.findByEmail(email);
//        Account account = newAccount.orElseThrow(() -> new RuntimeException("Account not found"));
//
//        return accountMapper.toResponse(account);
//
//    }

    public boolean isAccountPresent(long accountId) {
        return accountRepository.findById(accountId).isPresent();
    }


    @Override
    public List<Account> presentAccounts(List<Long> accountIds) {

        List<Account> accounts = new ArrayList<>();

        for (Long accountId : accountIds) {

            if (isAccountPresent(accountId)) {

                accounts.add(accountRepository.findById(accountId).get());

            }

        }

        return accounts;
    }


    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
    }

    @Override
    public Account findAccountByUserId(Long userId) {
        User user = userService.findById(userId);
        Account account = getAccountById(user.getAccount().getId());
        return account;
    }


    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return accountRepository.findByEmail(mail)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with mail: " +
                                        mail)
                );
    }


    public UserDetails loadUserById(long accountId) throws UsernameNotFoundException {
        return accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with id: " +
                                        accountId)
                );
    }
}
