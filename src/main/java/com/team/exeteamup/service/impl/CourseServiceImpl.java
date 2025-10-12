package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.CourseRequest;
import com.team.exeteamup.dto.request.CourseUpdateRequest;
import com.team.exeteamup.dto.response.CourseResponse;
import com.team.exeteamup.entity.Course;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.Semester;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.mapper.CourseMapper;
import com.team.exeteamup.repository.CourseRepository;
import com.team.exeteamup.repository.LecturerRepository;
import com.team.exeteamup.repository.SemesterRepository;
import com.team.exeteamup.service.CourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final SemesterRepository semesterRepository;
    private final LecturerRepository lecturerRepository;

    @Override
    public CourseResponse createCourse(CourseRequest courseRequest) {
        Course course  = courseMapper.toEntity(courseRequest);
        courseRepository.save(course);
        return courseMapper.toResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courseMapper.toResponseList(courses);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException("Lớp không tồn tại"));
        return courseMapper.toResponse(course);
    }

    @Override
    public List<CourseResponse> getCoursesBySemesterId(Long semesterId) {
        List<Course> courses = courseRepository.findBySemester_SemesterId(semesterId);
        return courseMapper.toResponseList(courses);
    }

    @Override
    public List<CourseResponse> getCoursesByLecturerId(Long lecturerId) {
        List<Course> courses = courseRepository.findByLecturer_LecturerId(lecturerId);
        return courseMapper.toResponseList(courses);
    }

    @Override
    public CourseResponse updateCourse(CourseUpdateRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new AppException("Lớp không tồn tại"));

        if (request.getSemesterId() != null) {
            Semester semester = semesterRepository.findById(request.getSemesterId())
                    .orElseThrow(() -> new AppException("Kì học không tồn tại"));
            course.setSemester(semester);
        }

        if (request.getLecturerId() != null) {
            Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                    .orElseThrow(() -> new AppException("Giảng viên không tồn tại"));
            course.setLecturer(lecturer);
        }

        courseMapper.updateEntity(course, request);
        Course updatedCourse = courseRepository.save(course);
        return courseMapper.toResponse(updatedCourse);
    }
}
