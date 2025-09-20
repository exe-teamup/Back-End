package com.team.exeteamup.service;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account create(String mail) {
        Account account = new Account();
        account.setMail(mail);
        return accountRepository.save(account);
    }
}
