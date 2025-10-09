package com.team.exeteamup.dto.response;

import com.team.exeteamup.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private long notificationId;
    private String title;
    private String notificationDetail;
    private NotificationType notificationType;
}
