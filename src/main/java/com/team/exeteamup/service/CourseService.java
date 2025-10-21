package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.CourseRequest;
import com.team.exeteamup.dto.request.CourseUpdateRequest;
import com.team.exeteamup.dto.response.CourseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CourseRequest courseRequest);
    List<CourseResponse> getAllCourses();
    CourseResponse getCourseById(Long id);
    List<CourseResponse> getCoursesBySemesterId(Long semesterId);
    List<CourseResponse> getCoursesByLecturerId(Long lecturerId);
    CourseResponse updateCourse(CourseUpdateRequest request);
    List<CourseResponse> importCoursesFromExcel(MultipartFile file);
    void deleteCourse(Long courseId);
}
