package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.ModeratorUpdateCourseRequest;
import com.team.exeteamup.dto.response.CourseResponse;
import com.team.exeteamup.entity.Course;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.mapper.CourseMapper;
import com.team.exeteamup.repository.CourseRepository;
import com.team.exeteamup.repository.LecturerRepository;
import com.team.exeteamup.service.inter.ModeratorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ModeratorServiceImpl implements ModeratorService {
    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseResponse updateCourseLecturer(Long courseId, ModeratorUpdateCourseRequest request) {
        Course course = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new AppException("Lớp học không tồn tại"));

        Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                .orElseThrow(() -> new AppException("Giảng viên không tồn tại"));

        course.setLecturer(lecturer);
        Course updatedCourse = courseRepository.save(course);
        return courseMapper.toResponse(updatedCourse);
    }
}
