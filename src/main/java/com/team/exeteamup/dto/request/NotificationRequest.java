package com.team.exeteamup.dto.request;

import com.team.exeteamup.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String notificationDetail;

    @NotNull
    private NotificationType notificationType;

}
