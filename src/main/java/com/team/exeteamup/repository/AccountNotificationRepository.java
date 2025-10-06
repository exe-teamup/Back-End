package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.AccountNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountNotificationRepository extends JpaRepository<AccountNotification, Long> {
}
