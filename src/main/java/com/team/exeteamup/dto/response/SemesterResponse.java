package com.team.exeteamup.dto.response;

import com.team.exeteamup.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterResponse {
    private long semesterId;
    private String semesterCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private SemesterStatus semesterStatus;
}
