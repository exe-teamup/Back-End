package com.team.exeteamup.repository;

import com.team.exeteamup.entity.JoinRequest;
import com.team.exeteamup.entity.Student;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findByStudent(Student student);
}
