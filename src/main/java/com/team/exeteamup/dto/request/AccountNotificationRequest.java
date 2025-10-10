package com.team.exeteamup.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountNotificationRequest {
    @NotNull
    private long notificationId;
    @NotEmpty
    private List<Long> accountIds;
}
