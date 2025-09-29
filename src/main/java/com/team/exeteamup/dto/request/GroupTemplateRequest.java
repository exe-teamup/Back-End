package com.team.exeteamup.dto.request;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupTemplateRequest {
    @Column(name = "min_member", nullable = false)
    private int min_member;

    @Column(name = "max_member", nullable = false)
    private int max_member;

    @Column(name = "min_major", nullable = false)
    private int min_major;

    @Column(name = "template", nullable = false, length = 255)
    private String template;
}
