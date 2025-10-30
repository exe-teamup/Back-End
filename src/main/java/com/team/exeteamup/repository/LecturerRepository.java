package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Lecturer findByAccount_AccountId(Long accountAccountId);
}
