package com.team.exeteamup.dto.response.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMemberResponse {
    private Long studentId;
    private String studentName;
    private Boolean isLeader;
    private String majorName;
}
