package com.team.exeteamup.controller;

import com.team.exeteamup.Exception.AppException;
import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.request.GroupUpdateRequest;
import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.mapper.GroupMapper;
import com.team.exeteamup.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("")
    public ResponseEntity<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        GroupResponse group = groupService.createGroup(groupRequest);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("{groupId}")
    public ResponseEntity<Map<String, String>> deleteGroup(@PathVariable long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok(Map.of("message", "Đã xóa nhóm thành công"));
    }

    @GetMapping("")
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @PutMapping("{groupId}")
    public ResponseEntity<GroupResponse> updateGroup(
            @PathVariable long groupId,
            @RequestBody GroupUpdateRequest request) {
        GroupResponse response = groupService.updateGroup(groupId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{groupId}")
    public ResponseEntity<?> getGroupById(@PathVariable long groupId) {
        GroupResponse response = groupService.getGroupById(groupId);
        return ResponseEntity.ok(response);
    }
}
