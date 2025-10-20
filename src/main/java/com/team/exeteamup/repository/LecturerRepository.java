package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
}
