package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.AccountRequest;
import com.team.exeteamup.dto.response.AccountResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.mapper.AccountMapper;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Account createAccount(AccountRequest email) {
        return null;
    }

    @Override
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
}
