package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.request.GroupUpdateRequest;
import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("")
    public ResponseEntity<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        GroupResponse group = groupService.createGroup(groupRequest);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> deleteGroup(@PathVariable long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok(Map.of("message", "Đã xóa nhóm thành công"));
    }

    @GetMapping("")
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        List<GroupResponse> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @PutMapping("{id}")
    public ResponseEntity<GroupResponse> updateGroup(
            @PathVariable long id,
            @RequestBody GroupUpdateRequest request) {
        GroupResponse response = groupService.updateGroup(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getGroupById(@PathVariable long id) {
        GroupResponse response = groupService.getGroupById(id);
        return ResponseEntity.ok(response);
    }
}
