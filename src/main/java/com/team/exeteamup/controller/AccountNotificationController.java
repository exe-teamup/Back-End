package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.AccountNotificationRequest;
import com.team.exeteamup.dto.response.AccountNotificationResponse;
import com.team.exeteamup.service.AccountNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-notifications")
public class AccountNotificationController {

    private final AccountNotificationService accountNotificationService;

    public AccountNotificationController(AccountNotificationService accountNotificationService) {
        this.accountNotificationService = accountNotificationService;
    }

    @GetMapping
    public ResponseEntity<List<AccountNotificationResponse>> getAllAccountNotifications() {
        List<AccountNotificationResponse> responses = accountNotificationService.getAccountNotifications();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<AccountNotificationResponse>> getAccountNotificationsByAccountId(
            @PathVariable long accountId) {
        List<AccountNotificationResponse> responses =
                accountNotificationService.getAccountNotificationsByAccountId(accountId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/notify")
    public ResponseEntity<List<AccountNotificationResponse>> notifyToAccounts(
            @RequestBody AccountNotificationRequest request) {
        List<AccountNotificationResponse> responses =
                accountNotificationService.sendNotificationToAccounts(request);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/check")
    public ResponseEntity<List<AccountNotificationResponse>> checkNotifications(
            @RequestBody List<Long> accountNotificationIds) {
        List<AccountNotificationResponse> responses =
                accountNotificationService.checkNotifications(accountNotificationIds);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/check/{id}")
    public ResponseEntity<AccountNotificationResponse> checkNotification(
            @PathVariable("id") long accountNotificationId) {
        AccountNotificationResponse response =
                accountNotificationService.checkNotification(accountNotificationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountNotificationResponse> getAccountNotificationById(
            @PathVariable("id") long accountNotificationId) {
        AccountNotificationResponse response =
                accountNotificationService.findResponseById(accountNotificationId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountNotificationResponse> deleteAccountNotification(
            @PathVariable("id") long accountNotificationId) {
        AccountNotificationResponse response =
                accountNotificationService.deleteAccountNotification(accountNotificationId);
        return ResponseEntity.ok(response);
    }
}
