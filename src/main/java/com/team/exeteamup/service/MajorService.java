package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.MajorRequest;
import com.team.exeteamup.dto.response.MajorResponse;
import lombok.RequiredArgsConstructor;

public interface MajorService {
    MajorResponse createMajor(MajorRequest majorRequest);
}
