package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.GroupRequest;
import com.team.exeteamup.dto.request.GroupUpdateRequest;
import com.team.exeteamup.dto.request.LecturerSelectionRequest;
import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.dto.response.LecturerSelectionResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.service.GroupRegisterLecturerService;
import com.team.exeteamup.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final GroupRegisterLecturerService groupRegisterLecturerService;

    @PostMapping("")
    public ResponseEntity<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        GroupResponse group = groupService.createGroup(groupRequest);
        return ResponseEntity.ok(group);
    }

    @PostMapping("/{id}/lecturers/select")
    public ResponseEntity<LecturerSelectionResponse> selectLecturers(
            @PathVariable Long id,
            @RequestBody LecturerSelectionRequest request) {
        LecturerSelectionResponse response = groupRegisterLecturerService.selectLecturers(id, request.getLecturerIds());
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<List<GroupResponse>> getAllGroups(
            @RequestParam(required = false) String status
    ) {
        List<GroupResponse> groups;
        if (status != null) {
            groups = groupService.getGroupsByStatus(status);
        } else {
            groups = groupService.getAllGroups();
        }
        return ResponseEntity.ok(groups);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getGroupById(@PathVariable long id) {
        GroupResponse response = groupService.getGroupById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<GroupResponse> updateGroup(
            @PathVariable long id,
            @RequestBody GroupUpdateRequest request) {
        GroupResponse response = groupService.updateGroup(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> deleteGroup(@PathVariable long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok(Map.of("message", "Đã xóa nhóm thành công"));
    }
}
