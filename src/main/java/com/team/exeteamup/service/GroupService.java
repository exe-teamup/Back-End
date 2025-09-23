package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.entity.Group;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    Group createGroup(GroupRequest groupRequest);
    void deleteGroup(UUID groupId);
    List<Group> getAllGroups();
}
