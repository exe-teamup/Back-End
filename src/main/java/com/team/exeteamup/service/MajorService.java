package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.MajorRequest;
import com.team.exeteamup.dto.response.MajorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MajorService {
    MajorResponse createMajor(MajorRequest majorRequest);
    List<MajorResponse> importMajors(MultipartFile file);
    List<MajorResponse> getAllMajors();
    List<MajorResponse> getMajorsByLevel(Long level);
    List<MajorResponse> getMajorsByParentMajorId(Long parentMajorId);
    MajorResponse updateMajor(Long id, MajorRequest majorRequest);
    void deleteMajor(Long majorId);
}
