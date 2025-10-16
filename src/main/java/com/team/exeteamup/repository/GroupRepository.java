package com.team.exeteamup.repository;

import com.team.exeteamup.dto.response.UserResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.GroupStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupIdAndGroupStatusTrue(Long groupId);
    List<Group> findByCourse_CourseId(Long courseId);


    @Query("""
        SELECT g FROM Group g
        WHERE  g.groupStatus = :status
    """)
    List<Group> findGroupByStatus(@Param("status") GroupStatus status);

}
