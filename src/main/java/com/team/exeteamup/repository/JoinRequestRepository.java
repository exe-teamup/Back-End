package com.team.exeteamup.repository;

import com.team.exeteamup.entity.JoinRequest;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.joinRequest.JoinRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findByUser(User user);
    long countByRequestStatus(JoinRequestStatus requestStatus);
}
