package com.team.exeteamup.controller;

import com.team.exeteamup.Exception.AppException;
import com.team.exeteamup.dto.request.LecturerRequest;
import com.team.exeteamup.dto.response.LecturerResponse;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lecturers")
@RequiredArgsConstructor
public class LecturerController {

    private final LecturerService lecturerService;

    @PostMapping(name = "import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Lecturer>> importLecturers(@RequestParam("file") MultipartFile file) {
        try {
            List<Lecturer> response = lecturerService.importStudentsFromExcel(file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateLecturer(
            @PathVariable Long id,
            @RequestBody LecturerRequest request) {
        try {
            LecturerResponse response = lecturerService.updateLecturer(id, request);
            return ResponseEntity.ok(response);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi cập nhật giảng viên");
        }
    }

    @GetMapping()
    public ResponseEntity <List<LecturerResponse>> getAllLecturers() {
        List<LecturerResponse> lecturerResponses = lecturerService.getAllLecturers();
        return ResponseEntity.ok(lecturerResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<LecturerResponse> getLecturer(@PathVariable Long id) {
        return ResponseEntity.ok(lecturerService.getLecturer(id));
    }
}
