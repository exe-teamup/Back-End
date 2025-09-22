package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.mapper.GroupMapper;
import com.team.exeteamup.service.GroupService;
import com.team.exeteamup.service.impl.GroupServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMapper groupMapper;

    @PostMapping("")
    public ResponseEntity<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        Group group =  groupService.createGroup(groupRequest);
        GroupResponse response = groupMapper.mapToGroupResponse(group);
        return ResponseEntity.ok(response);
    }
}
