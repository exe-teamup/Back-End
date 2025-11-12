package com.team.exeteamup.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String semesterName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate endDate;
    private SemesterStatus semesterStatus;
}
