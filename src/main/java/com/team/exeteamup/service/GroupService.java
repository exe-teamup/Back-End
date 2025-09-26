package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.Group;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    GroupResponse createGroup(GroupRequest groupRequest);
    void deleteGroup(long groupId);
    List<Group> getAllGroups();
}
