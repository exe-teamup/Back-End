package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
}
