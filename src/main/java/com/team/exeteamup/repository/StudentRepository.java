package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByAccount_Email(String email);
    Optional<Student> findByAccount_AccountId(Long accountId);
    Optional<Student> findByStudentId(Long studentId);
    Boolean existsByStudentCode(String studentCode);
    List<Student> findAllByGroup(Group group);
}
