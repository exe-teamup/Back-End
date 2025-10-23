package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.CourseRequest;
import com.team.exeteamup.dto.request.ModeratorUpdateCourseRequest;
import com.team.exeteamup.dto.response.CourseResponse;

public interface ModeratorService {
    CourseResponse updateCourseLecturer(Long courseId, ModeratorUpdateCourseRequest request);
}
