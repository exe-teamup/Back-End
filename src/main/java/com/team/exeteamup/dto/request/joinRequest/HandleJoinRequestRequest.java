package com.team.exeteamup.dto.request.joinRequest;

import com.team.exeteamup.enums.joinRequest.JoinRequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandleJoinRequestRequest {
    private String denyReason;
    @NotNull(message = "Thiếu status mới của join request")
    private JoinRequestStatus requestStatus;
}
