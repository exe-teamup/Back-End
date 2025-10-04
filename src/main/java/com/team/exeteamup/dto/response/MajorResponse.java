package com.team.exeteamup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MajorResponse {
    private Long majorId;
    private String majorName;
    private String majorCode;
    private Long parentMajorId;
    private String parentMajorName;
    private Long level;
}
