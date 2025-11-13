package com.team.exeteamup.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LecturerWorkLoadDTO {
    private Long lecturerId;
    private String lecturerName;
    private String email;
    private int currentLoad;
    private int quota;
    private double percentage;
    private String status;
}
