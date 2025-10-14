package com.team.exeteamup.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignLecturerRequest {
    private Long groupId;
    private Long lecturerId;
}
