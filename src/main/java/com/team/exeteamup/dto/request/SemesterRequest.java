package com.team.exeteamup.dto.request;

import com.team.exeteamup.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterRequest {
    private String semesterCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private SemesterStatus semesterStatus;
}
