package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.LecturerRequest;
import com.team.exeteamup.dto.response.LecturerResponse;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LecturerService {
    List<Lecturer> importStudentsFromExcel(MultipartFile file) throws IOException;
    LecturerResponse updateLecturer(Long lecturerId, LecturerRequest request);
}
