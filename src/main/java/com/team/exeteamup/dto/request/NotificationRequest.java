package com.team.exeteamup.dto.request;

import com.team.exeteamup.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class NotificationRequest {
    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String notificationDetail;

    @NotNull
    private NotificationType notificationType;

    private List<Long> accountIds;
}
