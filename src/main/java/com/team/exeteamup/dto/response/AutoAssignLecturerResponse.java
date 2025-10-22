package com.team.exeteamup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutoAssignLecturerResponse {
    private int totalGroups;
    private int successfullyAssigned;
    private int failToAssigned;
    private List<AutoAssignGroupResponse> assignedGroups;
}
