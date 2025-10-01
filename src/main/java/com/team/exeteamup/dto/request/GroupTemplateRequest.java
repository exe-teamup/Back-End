package com.team.exeteamup.dto.request;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupTemplateRequest {

    @NotNull
    @Positive
    private int min_member;

    @NotNull
    @Positive
    private int max_member;

    @NotNull
    @Positive
    private int min_major;

    @NotBlank
    @Size(max = 255)
    private String template;
}
