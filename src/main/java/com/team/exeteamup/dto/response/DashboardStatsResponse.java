package com.team.exeteamup.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsResponse {
    private long totalStudents;
    private long totalLecturers;
    private long activeSemesters;
    private long totalCourses;
    private long groupsCreatedToday;
    private long newPostsToday;
    private long pendingJoinRequests;
    private List<RecentActivityResponse> recentActivity;

    @Builder
    public static class RecentActivityResponse {
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        private LocalDateTime createdAt;
    }
}
