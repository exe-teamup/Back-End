package com.team.exeteamup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupTemplateResponse {
    private Long id;
    private Integer minMember;
    private Integer maxMember;
    private Integer minMajor;
    private String template;
}
