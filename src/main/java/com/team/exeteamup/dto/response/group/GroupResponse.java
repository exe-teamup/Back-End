package com.team.exeteamup.dto.response.group;

import com.team.exeteamup.enums.GroupStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {
    private long groupId;
    private String groupName;
    private GroupMemberResponse leader;
    private List<GroupMemberResponse> members;
    private TemplateResponse templates;
    private int memberCount;
    private GroupStatus groupStatus;
    private GroupCourseResponse course;
}
