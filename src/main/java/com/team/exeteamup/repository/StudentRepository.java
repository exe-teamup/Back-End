package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccount_Email(String email);
    Optional<User> findByAccount_AccountId(Long accountId);
    Optional<User> findByUserId(Long userId);
    Boolean existsByUserCode(String userCode);
    List<User> findAllByGroup(Group group);
}
