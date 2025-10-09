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

    @GetMapping("{id}")
    public ResponseEntity<SemesterResponse> getSemesterById(@PathVariable Long id) {
        return ResponseEntity.ok(semesterService.getSemesterById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<SemesterResponse> updateSemesterById(@PathVariable Long id, @RequestBody SemesterRequest semesterRequest) {
        return ResponseEntity.ok(semesterService.updateSemester(id, semesterRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSemesterById(@PathVariable Long id) {
        semesterService.deleteSemester(id);
        return ResponseEntity.ok(Map.of("message", "Đã xóa kì học thành công"));
    }
}
