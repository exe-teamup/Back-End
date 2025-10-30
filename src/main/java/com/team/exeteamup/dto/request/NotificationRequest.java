package com.team.exeteamup.dto.request;

import com.team.exeteamup.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Template code must contain only uppercase letters, numbers, and underscores.")
    private String templateCode;
    private String templateContent;
    private NotificationType notificationType;
}
