package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.AccountNotificationRequest;
import com.team.exeteamup.dto.response.AccountNotificationResponse;
import com.team.exeteamup.service.inter.notification.AccountNotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AccountNotificationController {

    private final AccountNotificationService accountNotificationService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority({'ADMIN', 'MODERATOR'})")
    public ResponseEntity<List<AccountNotificationResponse>> getAllAccountNotifications() {
        List<AccountNotificationResponse> responses =
                accountNotificationService.getAccountNotifications();
        return ResponseEntity.ok(responses);
    }


    @GetMapping("/account/{accountId}")
    @PreAuthorize("hasAnyAuthority({'STUDENT',  'LECTURER', 'STUDENT_LEADER', 'ADMIN', 'MODERATOR'})")
    public ResponseEntity<List<AccountNotificationResponse>> getAccountNotificationsByAccountId(
            @PathVariable long accountId) {
        List<AccountNotificationResponse> responses =
                accountNotificationService.getAccountNotificationsByAccountId(accountId);
        return ResponseEntity.ok(responses);
    }


    @PutMapping("/check")
    @PreAuthorize("hasAnyAuthority({'STUDENT',  'LECTURER', 'STUDENT_LEADER'})")
    public ResponseEntity<List<AccountNotificationResponse>> checkNotifications(
            @RequestBody List<Long> accountNotificationIds) {
        List<AccountNotificationResponse> responses =
                accountNotificationService.checkNotifications(accountNotificationIds);
        return ResponseEntity.ok(responses);
    }


    @PutMapping("/check/{id}")
    @PreAuthorize("hasAnyAuthority({'STUDENT',  'LECTURER', 'STUDENT_LEADER'})")
    public ResponseEntity<AccountNotificationResponse> checkNotification(
            @PathVariable("id") long accountNotificationId) {
        AccountNotificationResponse response =
                accountNotificationService.checkNotification(accountNotificationId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'STUDENT',  'LECTURER', 'STUDENT_LEADER'})")
    public ResponseEntity<AccountNotificationResponse> getAccountNotificationById(
            @PathVariable("id") long accountNotificationId) {
        AccountNotificationResponse response =
                accountNotificationService.findResponseById(accountNotificationId);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'STUDENT',  'LECTURER', 'STUDENT_LEADER'})")
    public ResponseEntity<AccountNotificationResponse> deleteAccountNotification(
            @PathVariable("id") long accountNotificationId) {
        AccountNotificationResponse response =
                accountNotificationService.deleteAccountNotification(accountNotificationId);
        return ResponseEntity.ok(response);
    }
}
