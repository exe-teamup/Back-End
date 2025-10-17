package com.team.exeteamup.service;

import com.google.api.gax.rpc.ServerStream;
import com.team.exeteamup.dto.request.MajorRequest;
import com.team.exeteamup.dto.response.MajorResponse;
import com.team.exeteamup.entity.Major;
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
    Major findById(Long majorId);

    List<Major> findAllByIds(List<Long> majorIds);
}
