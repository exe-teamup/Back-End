package com.team.exeteamup.dto.request.joinRequest;

import com.team.exeteamup.enums.joinRequest.JoinRequestType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestRequest {
    @NotNull(message = "Phải có thông tin sinh viên khi tạo lời mời hoặc yêu cầu vào nhóm")
    private long studentId;

    @NotNull(message = "Phải có thông tin của nhóm khi tạo lời mời hoặc yêu cầu vào nhóm")
    private long groupId;

    @NotNull(message = "Cần xác định là lời mời hay yêu cầu vào nhóm")
    private JoinRequestType requestType;
}
