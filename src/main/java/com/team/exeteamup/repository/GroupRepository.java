package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Group;
import com.team.exeteamup.enums.GroupStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Optional<Group> findByGroupIdAndGroupStatusTrue(Long groupId);
    List<Group> findByCourse_CourseId(Long courseId);

    @Query("""
        SELECT g FROM Group g
        WHERE  g.groupStatus = :status
    """)
    List<Group> findGroupByStatus(@Param("status") GroupStatus status);

    //fix
    @Query("SELECT g FROM Group g " +
            "WHERE g.memberCount >= (SELECT MAX(gt.maxMember) " +
            "FROM GroupTemplate gt)")
    List<Group> findFullGroups();

    @Query("SELECT g FROM Group g " +
            "WHERE g.memberCount < (SELECT MAX(gt.maxMember) " +
            "FROM GroupTemplate gt)")
    List<Group> findNotFullGroups();
    long countByCreatedAtAfter(LocalDateTime dateTime);

    List<Group> findAllByIsDeletedFalse();
}

