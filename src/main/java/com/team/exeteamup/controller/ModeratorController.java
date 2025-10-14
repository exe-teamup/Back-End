package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.AssignLecturerRequest;
import com.team.exeteamup.dto.request.AssignLecturerResponse;
import com.team.exeteamup.service.ModeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
