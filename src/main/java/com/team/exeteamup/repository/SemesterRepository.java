package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Semester;
import com.team.exeteamup.enums.SemesterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
    boolean existsBySemesterCode(String semesterCode);
    long countBySemesterStatus(SemesterStatus semesterStatus);
}
