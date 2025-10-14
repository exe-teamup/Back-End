package com.team.exeteamup.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class LecturerSelectionResponse {
    private Long groupId;
    private List<LecturerItem> selectedLecturers;

    @Data
    @Builder
    public static class LecturerItem{
        private Long lecturerId;
        private String lecturerName;
        private String lecturerEmail;
        private int registerOrder;
        private LocalDateTime createdAt;
    }
}
