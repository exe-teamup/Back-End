package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.dto.response.UserResponse;
import com.team.exeteamup.service.StudentProfileService;
import com.team.exeteamup.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentProfileService studentProfileService;
    private final StudentService studentService;

    private static final Set<String> VALID_SORT_FIELDS = new HashSet<>(Arrays.asList(
            "studentId", "fullName", "studentCode", "studentStatus", "createdAt", "leader"
    ));

    @GetMapping("profile")
    public ResponseEntity<?> getProfile(@RequestHeader(value = "Authorization", required = false) String token) {
        StudentProfileResponse response = studentProfileService.getStudentProfile(token);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<StudentProfileResponse> updateStudentProfile(
            @PathVariable Long id,
            @RequestBody StudentProfileRequest studentProfileRequest) {

        StudentProfileResponse response = studentProfileService.updateStudentProfile(id, studentProfileRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok(Map.of("message", "Xóa sinh viên thành công"));
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllStudents() {
        List<UserResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("without-group")
    public ResponseEntity<List<UserResponse>> getStudentsWithoutGroup() {
        List<UserResponse> users = studentService.getStudentWithoutGroup();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<UserResponse>> getAllStudents(
            @PageableDefault(size = 10, sort = "studentId", direction = Sort.Direction.ASC)
            Pageable pageable,
            @RequestParam(required = false) String sort) {
        if (sort != null) {
            String[] sortParams = sort.split(",");
            if (sortParams.length > 0 && !VALID_SORT_FIELDS.contains(sortParams[0])) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }
            if (sortParams.length > 1 && !sortParams[1].matches("^(asc|desc)$")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }
        }
        Page<UserResponse> studentPage = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(studentPage);
    }

    @PostMapping(value = "import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<List<UserResponse>> importStudents(@RequestParam("file") MultipartFile file) {
        try {
            List<UserResponse> response = studentService.importStudentsFromExcel(file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(value = "import-not-eligible", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importStudentNotEligible(@RequestParam("file") MultipartFile file) {
        try {
            studentService.importStudentsNotEligible(file);
            return ResponseEntity.ok("Đổi trạng thái sinh viên thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi thêm  sinh viên " + e.getMessage());
        }
    }
}
