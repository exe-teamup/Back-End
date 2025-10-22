package com.team.exeteamup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutoAssignGroupResponse {
    private Long groupId;
    private String groupName;
    private String lecturerName;
    private int registerOrder;
    private boolean assigned;
    private String message;
}
