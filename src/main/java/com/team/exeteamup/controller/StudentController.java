package com.team.exeteamup.controller;

import com.team.exeteamup.Exception.AppException;
import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.dto.response.StudentResponse;
import com.team.exeteamup.entity.Student;
import com.team.exeteamup.service.StudentProfileService;
import com.team.exeteamup.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentProfileService studentProfileService;
    private final StudentService studentService;

    @GetMapping("profile")
    public ResponseEntity<?> getProfile(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            StudentProfileResponse response = studentProfileService.getStudentProfile(token);
            return ResponseEntity.ok(response);

        } catch (AppException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token không hợp lệ hoặc đã hết hạn");
        }
    }

    @PutMapping("{studentId}")
    public ResponseEntity<?> updateStudentProfile(
            @PathVariable Long studentId,
            @RequestBody StudentProfileRequest studentProfileRequest) {
        try {
            StudentProfileResponse response = studentProfileService.updateStudentProfile(studentId, studentProfileRequest);
            return ResponseEntity.ok(response);
        } catch (AppException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi cập nhật profile");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
}
