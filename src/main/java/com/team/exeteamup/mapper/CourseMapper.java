package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.request.CourseRequest;
import com.team.exeteamup.dto.request.CourseUpdateRequest;
import com.team.exeteamup.dto.response.CourseResponse;
import com.team.exeteamup.entity.Course;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.Semester;
import com.team.exeteamup.enums.CourseStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequest request) {
        if (request == null) return null;

        Course course = new Course();
        applyRequestToCourse(course, request.getCourseCode(), request.getCourseName(),
                request.getMaxGroup(), request.getGroupCount(),
                request.getSemesterId(), request.getLecturerId(), request.getStatus());
        return course;
    }

    public CourseResponse toResponse(Course course) {
        if (course == null) return null;

        return CourseResponse.builder()
                .courseId(course.getCourseId())
                .courseCode(course.getCourseCode())
                .courseName(course.getCourseName())
                .maxGroup(course.getMaxGroup())
                .groupCount(course.getGroupCount())
                .semesterId(course.getSemester() != null ? course.getSemester().getSemesterId() : null)
                .lecturerId(course.getLecturer() != null ? course.getLecturer().getLecturerId() : null)
                .lecturerName(course.getLecturer() != null ? course.getLecturer().getFullName() : null)
                .semesterName(course.getSemester() != null ? course.getSemester().getSemesterName() : null)
                .status(course.getStatus())
                .build();
    }

    public List<CourseResponse> toResponseList(List<Course> courses) {
        return courses.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntity(Course course, CourseUpdateRequest request) {
        if (course == null || request == null) return;

        applyRequestToCourse(course, request.getCourseCode(), request.getCourseName(),
                request.getMaxGroup(), request.getGroupCount(),
                request.getSemesterId(), request.getLecturerId(), request.getStatus());
    }

    private void applyRequestToCourse(Course course, String code, String name,
                                      Integer maxGroup, Integer groupCount,
                                      Long semesterId, Long lecturerId, CourseStatus status) {
        if (code != null) {
            course.setCourseCode(code);
        }
        if (name != null) {
            course.setCourseName(name);
        }
        if (maxGroup != null) {
            course.setMaxGroup(maxGroup);
        }
        if (groupCount != null) {
            course.setGroupCount(groupCount);
        }
        if (status != null) {
            course.setStatus(status);
        }

        if (semesterId != null) {
            Semester semester = new Semester();
            semester.setSemesterId(semesterId);
            course.setSemester(semester);
        }

        if (lecturerId != null) {
            Lecturer lecturer = new Lecturer();
            lecturer.setLecturerId(lecturerId);
            course.setLecturer(lecturer);
        }
    }
}