package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.AssignLecturerRequest;
import com.team.exeteamup.dto.response.AssignLecturerResponse;
import com.team.exeteamup.service.ModeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moderator")
@RequiredArgsConstructor
public class ModeratorController {

    private final ModeratorService moderatorService;

    @PostMapping("/assign-lecturer")
    public ResponseEntity<AssignLecturerResponse> assignLecturer(@RequestBody AssignLecturerRequest request) {
        AssignLecturerResponse response = moderatorService.assignLecturer(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{groupId}/update-assigned-lecturer")
    public ResponseEntity<AssignLecturerResponse> updateAssignedLecturer(
            @PathVariable Long groupId,
            @RequestBody AssignLecturerRequest request) {
        request.setGroupId(groupId);
        AssignLecturerResponse response = moderatorService.updateAssignedLecturer(request);
        return ResponseEntity.ok(response);
    }
}
