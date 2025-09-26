package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
