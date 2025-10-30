package com.team.exeteamup.event.user;

import com.team.exeteamup.enums.event.UserGroupEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupEvent {
    private String userCode;
    private String groupName;
    private UserGroupEventType eventType;
}
