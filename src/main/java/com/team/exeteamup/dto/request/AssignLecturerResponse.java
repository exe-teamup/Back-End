package com.team.exeteamup.dto.request;

import com.team.exeteamup.enums.LecturerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignLecturerResponse {
    private Long groupId;
    private Long lecturerId;
    private String LecturerName;
    private LecturerStatus lecturerStatus;
    private Boolean isMain;
    private LocalDateTime assignedAt;

}
