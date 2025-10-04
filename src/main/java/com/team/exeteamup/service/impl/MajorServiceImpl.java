package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.MajorRequest;
import com.team.exeteamup.dto.response.MajorResponse;
import com.team.exeteamup.entity.Major;
import com.team.exeteamup.mapper.MajorMapper;
import com.team.exeteamup.repository.MajorRepository;
import com.team.exeteamup.service.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;
    private final MajorMapper majorMapper;

    @Override
    public MajorResponse createMajor(MajorRequest majorRequest) {
        Major major = new Major();
        major.setMajorName(majorRequest.getMajorName());
        major.setMajorCode(majorRequest.getMajorCode());
        major.setLevel(majorRequest.getLevel());

        if (majorRequest.getLevel() == 1) {
            major.setParentMajor(null);
        }

        else if (majorRequest.getLevel() == 2) {
            if (majorRequest.getParentMajorId() == null) {
                throw new RuntimeException("Cần id chuyên ngành cha cho level 2");
            }

            Major parent = majorRepository.findById(majorRequest.getParentMajorId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyên ngành cha"));

            if (parent.getLevel() != 1) {
                throw new RuntimeException("Chuyên ngành cha phải là level 1");
            }
            major.setParentMajor(parent);
        }
        Major saved = majorRepository.save(major);
        return majorMapper.toResponse(saved);
    }
}
