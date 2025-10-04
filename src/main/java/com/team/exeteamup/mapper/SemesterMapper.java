package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.SemesterResponse;
import com.team.exeteamup.entity.Semester;

public class SemesterMapper {
    public static SemesterResponse toResponse(Semester semester) {
        return SemesterResponse.builder()
                .semesterId(semester.getSemesterId())
                .semesterCode(semester.getSemesterCode())
                .startDate(semester.getStartDate())
                .endDate(semester.getEndDate())
                .semesterStatus(semester.getSemesterStatus())
                .build();
    }
}
