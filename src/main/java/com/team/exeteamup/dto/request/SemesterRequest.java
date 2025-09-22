package com.team.exeteamup.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterRequest {
    private UUID groupConstraintId;
    private String semesterCode;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String semesterStatus;
}
