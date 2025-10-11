package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.joinRequest.HandleJoinRequestRequest;
import com.team.exeteamup.dto.request.joinRequest.JoinRequestRequest;
import com.team.exeteamup.dto.response.JoinRequestResponse;
import com.team.exeteamup.entity.JoinRequest;

import java.util.List;

public interface JoinRequestService {
    JoinRequest findById(long joinRequestId);
    JoinRequestResponse findResponseById(long joinRequestId);
    List<JoinRequestResponse> findAll();
    List<JoinRequestResponse> findByUserId(long userId);
    JoinRequestResponse save(JoinRequestRequest request);
    JoinRequestResponse handleJoinRequest(HandleJoinRequestRequest handleJoinRequestRequest);
    JoinRequestResponse delete(long joinRequestId);
}
