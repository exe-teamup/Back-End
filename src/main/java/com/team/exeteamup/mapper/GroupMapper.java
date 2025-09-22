package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

    public GroupResponse mapToGroupResponse(Group group) {
        GroupResponse response = new GroupResponse();
        response.setGroupId(group.getGroupId());
        response.setGroupName(group.getGroupName());
        response.setMemberCount(group.getMemberCount());
        response.setGroupStatus(group.isGroupStatus());
        if (group.getLecturer() != null) {
            response.setLecturerId(group.getLecturer().getLecturerId()); // Giả sử Lecturer có phương thức getId()
        }
        return response;
    }
}