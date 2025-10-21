package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.request.GroupUpdateRequest;
import com.team.exeteamup.dto.response.group.GroupResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.enums.GroupFilterStatus;

import java.util.List;

public interface GroupService {
    GroupResponse createGroup(GroupRequest groupRequest);
    void deleteGroup(long groupId);
    List<GroupResponse> getAllGroups();
    GroupResponse updateGroup(long groupId, GroupUpdateRequest request);
    GroupResponse getGroupById(long groupId);
    Group findGroupById(long groupId);
    List<GroupResponse> getGroupsByStatus(String groupStatus);
    List<GroupResponse> getGroupsByCourseId(Long courseId);
    GroupResponse transferLeader(Long groupId, Long newLeaderId, String token);
    GroupResponse kickMember(Long groupId, Long memberId, String token);
    void leaveGroup(Long groupId, String token);
    GroupResponse addMember(Long groupId, Long memberId, String token);
    List<GroupResponse> filterGroups(GroupFilterStatus status);
}
