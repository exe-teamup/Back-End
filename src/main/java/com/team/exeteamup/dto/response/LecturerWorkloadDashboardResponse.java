package com.team.exeteamup.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LecturerWorkloadDashboardResponse {
    private long totalLecturers;
    private long lecturersWithSlot;
    private long lecturersAlmostFull;
    private long lecturersFull;
    private List<LecturerWorkLoadDTO> lecturerDetails;
}
