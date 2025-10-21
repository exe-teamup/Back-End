package com.team.exeteamup.dto.response.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LecturerPendingGroupsResponse {
    private Long lecturerId;
    private int totalGroups;
    private List<GroupRegisterLecturerResponse> groups;
}
