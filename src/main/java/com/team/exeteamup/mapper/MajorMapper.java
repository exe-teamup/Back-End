package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.MajorResponse;
import com.team.exeteamup.entity.Major;
import org.springframework.stereotype.Component;

@Component
public class MajorMapper {
    public static MajorResponse toResponse(Major major) {
        return MajorResponse.builder()
                .majorId(major.getMajorId())
                .majorName(major.getMajorName())
                .majorCode(major.getMajorCode())
                .parentMajorId(
                        major.getParentMajor() != null ? major.getParentMajor().getMajorId() : null
                )
                .parentMajorName(
                        major.getParentMajor() != null ? major.getParentMajor().getMajorName() : null
                )
                .level(major.getLevel())
                .build();
    }

}
