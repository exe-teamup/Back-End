package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.NotificationRequest;
import com.team.exeteamup.dto.response.NotificationResponse;
import com.team.exeteamup.service.inter.notification.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN'})")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        List<NotificationResponse> notifications = notificationService.findAllNotifications();
        return ResponseEntity.ok(notifications);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'LECTURER', 'STUDENT'})")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable("id") long id) {
        NotificationResponse response = notificationService.findResponseById(id);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN'})")
    public ResponseEntity<NotificationResponse> createNotification(@Valid @RequestBody NotificationRequest request) {
        NotificationResponse response = notificationService.saveNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN'})")
    public ResponseEntity<NotificationResponse> updateNotification(@PathVariable("id") long id,
                                                                   @Valid @RequestBody NotificationRequest request) {
        NotificationResponse response = notificationService.updateNotification(id, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN'})")
    public ResponseEntity<NotificationResponse> deleteNotification(@PathVariable("id") long id) {
        NotificationResponse response = notificationService.deleteNotification(id);
        return ResponseEntity.ok(response);
    }
}
