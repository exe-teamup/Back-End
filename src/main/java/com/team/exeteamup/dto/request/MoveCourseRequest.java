package com.team.exeteamup.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MoveCourseRequest {

    private Long newCourseId;
}
