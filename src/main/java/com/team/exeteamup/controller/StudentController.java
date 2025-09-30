package com.team.exeteamup.controller;

import com.team.exeteamup.Exception.AppException;
import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.dto.response.StudentResponse;
import com.team.exeteamup.entity.Student;
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

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long studentId) {
        Map<String, String> response = new HashMap<>();
        try {
            studentService.deleteStudentById(studentId);
            response.put("message", "Xóa sinh viên thành công");
            return ResponseEntity.ok(response);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("error", "Đã xảy ra lỗi không mong muốn");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<StudentResponse>> getAllStudents(
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
        Page<StudentResponse> studentPage = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(studentPage);
    }

    @PostMapping(value = "import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<List<Student>> importStudents(@RequestParam("file") MultipartFile file) {
        try {
            List<Student> response = studentService.importStudentsFromExcel(file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
