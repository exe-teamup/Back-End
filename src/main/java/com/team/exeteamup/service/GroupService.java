package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.entity.Group;

public interface GroupService {
    Group createGroup(GroupRequest groupRequest);
}
