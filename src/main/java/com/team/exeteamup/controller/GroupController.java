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
import java.util.UUID;

@RestController
@RequestMapping("group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMapper groupMapper;

    @PostMapping("")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest) {
        try {
            GroupResponse group = groupService.createGroup(groupRequest);
            return ResponseEntity.ok(group);
        } catch (AppException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi tạo group");
        }
    }

    @DeleteMapping("{groupId}")
    public ResponseEntity<Map<String, String>>deleteGroup(@PathVariable long groupId) {
        groupService.deleteGroup(groupId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đã xóa nhóm thành công");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @PutMapping("{groupId}")
    public ResponseEntity<?> updateGroup(
            @PathVariable long groupId,
            @RequestBody GroupUpdateRequest request) {
        try {
            GroupResponse response = groupService.updateGroup(groupId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
