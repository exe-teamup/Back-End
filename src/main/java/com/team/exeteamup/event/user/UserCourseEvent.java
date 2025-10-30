package com.team.exeteamup.event.user;

import com.team.exeteamup.enums.event.UserCourseEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseEvent {
    private String userCode;
    private String courseCode;
    private UserCourseEventType eventType;
}
