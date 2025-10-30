package com.team.exeteamup.service.inter;

import com.team.exeteamup.dto.request.LecturerRequest;
import com.team.exeteamup.dto.response.CourseResponse;
import com.team.exeteamup.dto.response.SemesterResponse;
import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.dto.response.lecturer.LecturerResponse;
import com.team.exeteamup.entity.Lecturer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LecturerService {
    List<Lecturer> importStudentsFromExcel(MultipartFile file) throws IOException;
    LecturerResponse updateLecturer(Long lecturerId, LecturerRequest request);
    List<LecturerResponse> getAllLecturers();
    LecturerResponse getLecturer(Long lecturerId);
    LecturerResponse deleteLecturer(Long lecturerId);
    Lecturer findById(Long lecturerId);
    LecturerResponse getCurrentLecturer();
}
