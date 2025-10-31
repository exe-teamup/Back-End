package com.team.exeteamup.event.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupUpdateEvent {
    private String updaterUserName;
}
