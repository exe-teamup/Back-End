package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.request.CourseRequest;
import com.team.exeteamup.dto.request.CourseUpdateRequest;
import com.team.exeteamup.dto.response.CourseResponse;
import com.team.exeteamup.entity.Course;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.Semester;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequest request) {
        if (request == null) return null;

        Course course = new Course();
        course.setCourseCode(request.getCourseCode());
        course.setMaxGroup(request.getMaxGroup());
        course.setGroupCount(request.getGroupCount());

        if (request.getSemesterId() != null) {
            Semester semester = new Semester();
            semester.setSemesterId(request.getSemesterId());
            course.setSemester(semester);
        }

        if (request.getLecturerId() != null) {
            Lecturer lecturer = new Lecturer();
            lecturer.setLecturerId(request.getLecturerId());
            course.setLecturer(lecturer);
        }
        return course;
    }

    public CourseResponse toResponse(Course course) {
        if (course == null) return null;

        return CourseResponse.builder()
                .courseId(course.getCourseId())
                .courseCode(course.getCourseCode())
                .maxGroup(course.getMaxGroup())
                .groupCount(course.getGroupCount())
                .semesterId(course.getSemester() != null ? course.getSemester().getSemesterId() : null)
                .lecturerId(course.getLecturer() != null ? course.getLecturer().getLecturerId() : null)
                .build();
    }

    public List<CourseResponse> toResponseList(List<Course> courses) {
        return courses.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntity(Course course, CourseUpdateRequest request) {
        course.setCourseCode(request.getCourseCode());
        course.setMaxGroup(request.getMaxGroup());
        course.setGroupCount(request.getGroupCount());

        if (request.getSemesterId() != null) {
            Semester semester = new Semester();
            semester.setSemesterId(request.getSemesterId());
            course.setSemester(semester);
        }

        if (request.getLecturerId() != null) {
            Lecturer lecturer = new Lecturer();
            lecturer.setLecturerId(request.getLecturerId());
            course.setLecturer(lecturer);
        }
    }
}