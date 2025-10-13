package com.team.exeteamup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {
    private long groupId;
    private String groupName;
    private List<Long> memberIds;
    private int memberCount;
    private Boolean groupStatus;
    private Long courseId;
}
