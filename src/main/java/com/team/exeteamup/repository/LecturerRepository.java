package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Optional<Lecturer> findByAccount_AccountId(Long accountAccountId);
}
