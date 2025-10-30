package com.team.exeteamup.service.inter;

import com.team.exeteamup.dto.request.ModeratorUpdateCourseRequest;
import com.team.exeteamup.dto.response.CourseResponse;

public interface ModeratorService {
    CourseResponse updateCourseLecturer(Long courseId, ModeratorUpdateCourseRequest request);
}
