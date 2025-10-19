package com.team.exeteamup.dto.request;


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
    private int minMember;

    @NotNull
    @Positive
    private int maxMember;

    @NotNull
    @Positive
    private int minMajor;

    @NotBlank
    @Size(max = 255)
    private String template;
}
