package com.team.exeteamup.dto.response;

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
public class LecturerResponse {
    private long lecturerId;
    private String lecturerName;
    private String lecturerStatus;
    private String accountStatus;
    private long accountId;
}
