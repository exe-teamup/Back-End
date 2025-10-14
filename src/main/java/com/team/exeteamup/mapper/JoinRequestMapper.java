package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.JoinRequestResponse;
import com.team.exeteamup.entity.JoinRequest;
import org.springframework.stereotype.Component;

@Component
public class JoinRequestMapper {
    public JoinRequestResponse toResponse(JoinRequest joinRequest) {
        return JoinRequestResponse.builder()
                .id(joinRequest.getId())
                .studentId(joinRequest.getUser().getUserId())
                .groupId(joinRequest.getGroup().getGroupId())
                .createdAt(joinRequest.getCreatedAt())
                .requestStatus(joinRequest.getRequestStatus())
                .denyReason(joinRequest.getDenyReason())
                .requestType(joinRequest.getRequestType())
                .build();
    }
}
