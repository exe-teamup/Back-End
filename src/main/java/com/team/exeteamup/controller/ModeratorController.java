package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.ModeratorUpdateCourseRequest;
import com.team.exeteamup.dto.response.CourseResponse;
import com.team.exeteamup.service.inter.ModeratorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moderator")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ModeratorController {

    private final ModeratorService moderatorService;


    @PutMapping("{courseId}/lecturer")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<CourseResponse> updateCourseLecturer(
            @PathVariable Long courseId,
            @RequestBody ModeratorUpdateCourseRequest request) {
        CourseResponse response = moderatorService.updateCourseLecturer(courseId, request);
        return ResponseEntity.ok(response);
    }
}
