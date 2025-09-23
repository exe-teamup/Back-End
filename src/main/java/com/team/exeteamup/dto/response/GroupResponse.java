package com.team.exeteamup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {
    private UUID groupId;
//    private UUID semesterId;
    private UUID lecturerId;
//    private UUID courseId;
    private String groupName;
    private int memberCount;
    private Boolean groupStatus;
}
