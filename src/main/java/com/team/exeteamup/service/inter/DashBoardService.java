package com.team.exeteamup.service.inter;

import com.team.exeteamup.dto.response.DashboardStatsResponse;
import com.team.exeteamup.dto.response.LecturerWorkloadDashboardResponse;

public interface DashBoardService {
    DashboardStatsResponse getDashboardStats();
    LecturerWorkloadDashboardResponse getLecturerWorkloadDashboard();
}
