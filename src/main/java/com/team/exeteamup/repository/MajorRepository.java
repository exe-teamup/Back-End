package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findByMajorStatusIsTrue();
    List<Major> findByLevelAndMajorStatusIsTrue(Long level);
    List<Major> findByParentMajor_MajorIdAndMajorStatusIsTrue(Long majorId);
    Optional<Major> findByMajorName(String majorName);
    boolean existsByMajorCode(String majorCode);
    boolean existsByMajorName(String majorName);
}
