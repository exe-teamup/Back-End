package com.team.exeteamup.controller;

import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.Student;
import com.team.exeteamup.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
}
