package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.NotificationRequest;
import com.team.exeteamup.dto.response.NotificationResponse;
import com.team.exeteamup.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("")
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody NotificationRequest request) {
        NotificationResponse response = notificationService.saveNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotification(@PathVariable long id) {
        return ResponseEntity.ok(notificationService.getNotification(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> updateNotification(@PathVariable long id,
                                                                   @RequestBody NotificationRequest request)
    {
        return ResponseEntity.ok(notificationService.updateNotification(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NotificationResponse> deleteNotification(@PathVariable long id) {
        return ResponseEntity.ok(notificationService.deleteNotification(id));
    }
}
