package com.team.exeteamup.dto.request;

import com.team.exeteamup.enums.GroupStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupUpdateRequest {
    private String groupName;
    private GroupStatus groupStatus;
}
