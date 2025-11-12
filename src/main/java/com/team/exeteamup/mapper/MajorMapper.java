package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.MajorResponse;
import com.team.exeteamup.entity.Major;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MajorMapper {
    public MajorResponse toResponse(Major major) {
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
                .majorStatus(major.getMajorStatus())
                .build();
    }

    public List<MajorResponse> toResponseList(List<Major> majors) {
        return majors.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
