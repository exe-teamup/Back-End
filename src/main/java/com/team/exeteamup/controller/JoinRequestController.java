package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.joinRequest.HandleJoinRequestRequest;
import com.team.exeteamup.dto.request.joinRequest.JoinRequestRequest;
import com.team.exeteamup.dto.response.JoinRequestResponse;
import com.team.exeteamup.service.JoinRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/join-requests")
@RequiredArgsConstructor
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    @PostMapping
    public ResponseEntity<JoinRequestResponse> createJoinRequest(
            @Valid @RequestBody JoinRequestRequest request) {
        JoinRequestResponse response = joinRequestService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JoinRequestResponse> getJoinRequestById(
            @PathVariable("id") long joinRequestId) {
        return ResponseEntity.ok(joinRequestService.findResponseById(joinRequestId));
    }

    @GetMapping
    public ResponseEntity<List<JoinRequestResponse>> getAll() {
        return ResponseEntity.ok(joinRequestService.findAll());
    }

    @PatchMapping
    public ResponseEntity<JoinRequestResponse> handleJoinRequest(
            @Valid @RequestBody HandleJoinRequestRequest request) {
        return ResponseEntity.ok(joinRequestService.handleJoinRequest(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JoinRequestResponse> deleteJoinRequest(
            @PathVariable("id") long joinRequestId) {
        return ResponseEntity.ok(joinRequestService.delete(joinRequestId));
    }
}
