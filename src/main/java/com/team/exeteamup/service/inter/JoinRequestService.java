package com.team.exeteamup.service.inter;

import com.team.exeteamup.dto.request.joinRequest.HandleJoinRequestRequest;
import com.team.exeteamup.dto.request.joinRequest.JoinRequestRequest;
import com.team.exeteamup.dto.response.JoinRequestResponse;
import com.team.exeteamup.entity.JoinRequest;
import com.team.exeteamup.enums.joinRequest.JoinRequestType;

import java.util.List;

public interface JoinRequestService {
    JoinRequest findById(long joinRequestId);
    JoinRequestResponse findResponseById(long joinRequestId);
    List<JoinRequestResponse> findAll();
    List<JoinRequestResponse> findByStudentId(long studentId);
    JoinRequestResponse save(JoinRequestRequest request);
    JoinRequestResponse handleJoinRequest(long joinRequestId, HandleJoinRequestRequest handleJoinRequestRequest);
    JoinRequestResponse delete(long joinRequestId);
    List<JoinRequestResponse> findAllByFilter(Long userId, JoinRequestType joinRequestType);
}
