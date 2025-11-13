package com.team.exeteamup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountNotificationResponse {
    private long accountNotificationId;
    private long notificationId;
    private long accountId;
    private boolean isChecked;
    private String formattedContent;
    private LocalDateTime createdAt;
}
