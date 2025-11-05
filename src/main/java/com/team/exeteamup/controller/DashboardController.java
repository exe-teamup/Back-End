package com.team.exeteamup.controller;

import com.team.exeteamup.dto.response.DashboardStatsResponse;
import com.team.exeteamup.service.inter.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashBoardService dashBoardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsResponse> getDashboardStats() {
        DashboardStatsResponse response = dashBoardService.getDashboardStats();
        return ResponseEntity.ok(response);
    }
}
