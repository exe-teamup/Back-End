package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccount_Email(String email);
    Optional<User> findByAccount_AccountId(Long accountId);
    Optional<User> findByUserId(Long userId);
    Boolean existsByUserCode(String userCode);
    List<User> findAllByGroup(Group group);
    List<User> findByGroupIsNull();

    @Query("SELECT COUNT(u) FROM User u WHERE u.group.groupId = :groupId")
    int countByGroup_GroupId(Long groupId);

    @Query("SELECT u FROM User u " +
            "JOIN u.account a " +
            "WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(u.userCode) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(a.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchStudents(@Param("keyword") String keyword);
}
