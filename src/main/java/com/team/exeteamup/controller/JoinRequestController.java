package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.joinRequest.HandleJoinRequestRequest;
import com.team.exeteamup.dto.request.joinRequest.JoinRequestRequest;
import com.team.exeteamup.dto.response.JoinRequestResponse;
import com.team.exeteamup.enums.joinRequest.JoinRequestType;
import com.team.exeteamup.service.inter.JoinRequestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/join-requests")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;


    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<JoinRequestResponse> createJoinRequest(@Valid @RequestBody JoinRequestRequest request) {
        JoinRequestResponse response = joinRequestService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'STUDENT', 'MODERATOR', 'ADMIN'})")
    public ResponseEntity<JoinRequestResponse> getJoinRequestById(@PathVariable("id") long joinRequestId) {
        return ResponseEntity.ok(joinRequestService.findResponseById(joinRequestId));
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'STUDENT'})")
    public ResponseEntity<List<JoinRequestResponse>> getAll(@RequestParam(required = false) Long userId,
                                                            @RequestParam(required = false) JoinRequestType joinRequestType) {
        return ResponseEntity.ok(joinRequestService.findAllByFilter(userId, joinRequestType));
    }


    @GetMapping("/find-by-student/{id}")
    @PreAuthorize("hasAnyAuthority({'STUDENT', 'MODERATOR', 'ADMIN'})")
    public ResponseEntity<List<JoinRequestResponse>> getAllByUserId(@PathVariable("id") long studentId) {
        return ResponseEntity.ok(joinRequestService.findByStudentId(studentId));
    }


    @PatchMapping("/handle-request/{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<JoinRequestResponse> handleJoinRequest(@PathVariable("id") long joinRequestId,
                                                                 @Valid @RequestBody HandleJoinRequestRequest request) {
        return ResponseEntity.ok(joinRequestService.handleJoinRequest(joinRequestId, request));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'STUDENT', 'MODERATOR', 'ADMIN'})")
    public ResponseEntity<JoinRequestResponse> deleteJoinRequest(@PathVariable("id") long joinRequestId) {
        return ResponseEntity.ok(joinRequestService.delete(joinRequestId));
    }
}
