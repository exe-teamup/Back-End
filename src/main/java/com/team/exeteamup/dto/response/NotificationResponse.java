package com.team.exeteamup.dto.response;

import com.team.exeteamup.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private long id;
    private String templateCode;
    private String templateContent;
    private NotificationType notificationType;
}
