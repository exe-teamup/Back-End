package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.CourseRequest;
import com.team.exeteamup.dto.request.CourseUpdateRequest;
import com.team.exeteamup.dto.response.CourseResponse;
import com.team.exeteamup.service.inter.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("")
    public ResponseEntity<CourseResponse> createCourses(@RequestBody CourseRequest courseRequest) {
        CourseResponse course = courseService.createCourse(courseRequest);
        return ResponseEntity.ok(course);
    }

    @PostMapping(value = "import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<CourseResponse>> importCourses(@RequestParam("file") MultipartFile file) {
        List<CourseResponse> responses = courseService.importCoursesFromExcel(file);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> course = courseService.getAllCourses();
        return ResponseEntity.ok(course);
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable("id") Long id) {
        CourseResponse courseResponse = courseService.getCourseById(id);
        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping("semester/{id}")
    public ResponseEntity<List<CourseResponse>> getCourseBySemesterId(@PathVariable("id") Long id) {
        List<CourseResponse> courseResponses = courseService.getCoursesBySemesterId(id);
        return ResponseEntity.ok(courseResponses);
    }

    @GetMapping("lecturer/{id}")
    public ResponseEntity<List<CourseResponse>> getCoursesByLecturerId(@PathVariable("id") Long id) {
        List<CourseResponse> courseResponses = courseService.getCoursesByLecturerId(id);
        return ResponseEntity.ok(courseResponses);
    }



    @PutMapping("{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable("id") Long id, @RequestBody CourseUpdateRequest request) {
        CourseResponse response = courseService.updateCourse(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Xóa lớp thành công");
    }
}
