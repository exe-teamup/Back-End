package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.mapper.GroupMapper;
import com.team.exeteamup.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @DeleteMapping("{groupId}")
    public ResponseEntity<Map<String, String>>deleteGroup(@PathVariable UUID groupId) {
        groupService.deleteGroup(groupId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đã xóa nhóm thành công");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
