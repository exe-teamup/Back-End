package com.team.exeteamup.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.exeteamup.enums.joinRequest.JoinRequestStatus;
import com.team.exeteamup.enums.joinRequest.JoinRequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequestResponse {
    private long id;

    private long studentId;

    private long groupId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    private JoinRequestStatus requestStatus;

    private String denyReason;

    private JoinRequestType requestType;
}
