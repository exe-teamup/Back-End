package com.team.exeteamup.dto.request.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMajorRequest {
    private Long majorId;
    private Integer studentNum;
}
