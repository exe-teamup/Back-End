package com.team.exeteamup.event.user;

import com.team.exeteamup.enums.event.UserJoinRequestEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinRequestEvent {
    private String userCode;
    private String groupName;
    private UserJoinRequestEventType eventType;
}
