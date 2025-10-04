package com.team.exeteamup.dto.request;

import com.team.exeteamup.enums.LecturerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LecturerRequest {
    private LecturerStatus status;
}
