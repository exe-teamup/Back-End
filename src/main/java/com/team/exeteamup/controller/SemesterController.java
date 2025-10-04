package com.team.exeteamup.controller;

import com.team.exeteamup.Exception.AppException;
import com.team.exeteamup.dto.request.SemesterRequest;
import com.team.exeteamup.dto.response.SemesterResponse;
import com.team.exeteamup.service.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/semesters")
@RequiredArgsConstructor
public class SemesterController {
    private final SemesterService semesterService;

    @PostMapping("")
    public ResponseEntity<SemesterResponse> createSemester(@RequestBody SemesterRequest semesterRequest) {
        SemesterResponse semesterResponse = semesterService.createSemester(semesterRequest);
        return ResponseEntity.ok(semesterResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<SemesterResponse>> getAllSemesters() {
        return ResponseEntity.ok(semesterService.getAllSemesters());
    }

    @GetMapping("{semesterId}")
    public ResponseEntity<SemesterResponse> getSemesterById(@PathVariable Long semesterId) {
        return ResponseEntity.ok(semesterService.getSemesterById(semesterId));
    }

    @PutMapping("{semesterId}")
    public ResponseEntity<SemesterResponse> updateSemesterById(@PathVariable Long semesterId, @RequestBody SemesterRequest semesterRequest) {
        return ResponseEntity.ok(semesterService.updateSemester(semesterId, semesterRequest));
    }

    @DeleteMapping("/{semesterId}")
    public ResponseEntity<Map<String, String>> deleteSemesterById(@PathVariable Long semesterId) {
        semesterService.deleteSemester(semesterId);
        return ResponseEntity.ok(Map.of("message", "Đã xóa kì học thành công"));
    }
}
