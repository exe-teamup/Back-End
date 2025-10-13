package com.team.exeteamup.repository;

import com.team.exeteamup.dto.response.CourseResponse;
import com.team.exeteamup.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findBySemester_SemesterId(Long semesterId);
    List<Course> findByLecturer_LecturerId(Long lecturerId);
    Optional<Course> findByCourseCode(String courseCode);
}
