package com.team.exeteamup.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRequest {
    private UUID semesterId;
    private UUID lecturerId;
    private String classCode;
    private int maxGroup;
    private int groupCount;
}
