package com.team.exeteamup.controller;

import com.team.exeteamup.Exception.AppException;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentProfileService studentProfileService;

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
}
