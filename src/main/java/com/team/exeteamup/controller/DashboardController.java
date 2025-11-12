package com.team.exeteamup.controller;

import com.team.exeteamup.dto.response.DashboardStatsResponse;
import com.team.exeteamup.service.inter.DashBoardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dashboard")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final DashBoardService dashBoardService;


    @GetMapping("/stats")
    @PreAuthorize("hasAnyAuthority({'ADMIN', 'MODERATOR'})")
    public ResponseEntity<DashboardStatsResponse> getDashboardStats() {
        DashboardStatsResponse response = dashBoardService.getDashboardStats();
        return ResponseEntity.ok(response);
    }
}
