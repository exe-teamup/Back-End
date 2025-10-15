package com.team.exeteamup.repository;

import com.team.exeteamup.dto.response.UserResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupIdAndGroupStatusTrue(Long groupId);
    List<Group> findByCourse_CourseId(Long courseId);
}
