package com.team.exeteamup.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupRequest {
    private Long courseId;
    private String groupName;
    private long studentId;
    private List<String> memberEmails;
}
