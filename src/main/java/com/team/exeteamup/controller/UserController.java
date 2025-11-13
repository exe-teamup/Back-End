package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.MoveCourseRequest;
import com.team.exeteamup.dto.request.StudentProfileRequest;
import com.team.exeteamup.dto.request.SwapRequest;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.dto.response.UserResponse;
import com.team.exeteamup.service.inter.UserProfileService;
import com.team.exeteamup.service.inter.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserProfileService userProfileService;
    private final UserService userService;

    private static final Set<String> VALID_SORT_FIELDS = new HashSet<>(Arrays.asList(
            "studentId", "fullName", "studentCode", "studentStatus", "createdAt", "leader"
    ));


    @GetMapping("profile")
    @PreAuthorize("hasAuthority({'STUDENT'})")
    public ResponseEntity<?> getProfile() {
        Object response = userProfileService.getProfile();
        return ResponseEntity.ok(response);
    }


    @PutMapping("{id}")
    @PreAuthorize("hasAuthority({'STUDENT'})")
    public ResponseEntity<StudentProfileResponse> updateStudentProfile(
            @PathVariable Long id,
            @RequestBody StudentProfileRequest studentProfileRequest) {

        StudentProfileResponse response = userProfileService.updateStudentProfile(id, studentProfileRequest);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN'})")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        userService.deleteStudentById(id);
        return ResponseEntity.ok(Map.of("message", "Xóa sinh viên thành công"));
    }


    @GetMapping("")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'LECTURER'})")
    public ResponseEntity<List<UserResponse>> getAllStudents(@RequestParam(required = false) Long majorId,
                                                             @RequestParam(required = false) Long courseId,
                                                             @RequestParam(required = false) Boolean isLeader,
                                                             @RequestParam(required = false) Boolean hasGroup) {
        List<UserResponse> students = userService.getUserByFilter(majorId, courseId, isLeader, hasGroup);
        return ResponseEntity.ok(students);
    }


    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getStudentById(@PathVariable Long id) {
        UserResponse response = userService.getStudentById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("without-group")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'LECTURER'})")
    public ResponseEntity<List<UserResponse>> getStudentsWithoutGroup() {
        List<UserResponse> users = userService.getStudentWithoutGroup();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'LECTURER', 'STUDENT'})")
    public ResponseEntity<List<UserResponse>> searchStudents(
            @RequestParam("keyword") String keyword) {
        List<UserResponse> result = userService.searchStudents(keyword);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'LECTURER'})")
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
        Page<UserResponse> studentPage = userService.getAllStudents(pageable);
        return ResponseEntity.ok(studentPage);
    }


    @PostMapping(value = "import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN'})")
        public ResponseEntity<List<UserResponse>> importStudents(@RequestParam("file") MultipartFile file) {
        try {
            List<UserResponse> response = userService.importStudentsFromExcel(file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PostMapping(value = "import-not-eligible", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN'})")
    public ResponseEntity<?> importStudentNotEligible(@RequestParam("file") MultipartFile file) {
        try {
            userService.importStudentsNotEligible(file);
            return ResponseEntity.ok("Đổi trạng thái sinh viên thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi thêm  sinh viên " + e.getMessage());
        }
    }


    @PostMapping("/swap-course")
    public ResponseEntity<Map<String, UserResponse>> swapStudentCourse(
            @Valid @RequestBody SwapRequest request) {
        Map<String, UserResponse> responses =  userService.swapStudentCourse(request);
        return ResponseEntity.ok(responses);
    }


    @PutMapping("/move-course")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<UserResponse> moveStudentCourse(
            @Valid @RequestBody MoveCourseRequest request) {
        UserResponse response = userService.moveStudentCourses(request.getNewCourseId());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/course/{id}")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'LECTURER', 'STUDENT'})")
    public ResponseEntity<List<UserResponse>> getByCourseId(@PathVariable Long id) {
        List<UserResponse> result = userService.getStudentByCourseId(id);
        return ResponseEntity.ok(result);
    }
}
