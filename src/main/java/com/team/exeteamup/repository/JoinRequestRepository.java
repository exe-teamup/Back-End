package com.team.exeteamup.repository;

import com.team.exeteamup.entity.JoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findByUserId(Long userId);
}
