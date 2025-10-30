package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.ModeratorUpdateCourseRequest;
import com.team.exeteamup.dto.response.CourseResponse;
import com.team.exeteamup.service.ModeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moderator")
@RequiredArgsConstructor
public class ModeratorController {

    private final ModeratorService moderatorService;

    @PutMapping("{courseId}/lecturer")
    public ResponseEntity<CourseResponse> updateCourseLecturer(
            @PathVariable Long courseId,
            @RequestBody ModeratorUpdateCourseRequest request) {
        CourseResponse response = moderatorService.updateCourseLecturer(courseId, request);
        return ResponseEntity.ok(response);
    }
}
