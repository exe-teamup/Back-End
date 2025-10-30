package com.team.exeteamup.event.groupLeader;

import com.team.exeteamup.enums.event.GroupInvitationEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupInvitationResponseEvent {
    private String userCode;
    private String denyReason;
    private GroupInvitationEventType eventType;
}
