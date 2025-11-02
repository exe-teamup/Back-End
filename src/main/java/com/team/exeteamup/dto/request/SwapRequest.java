package com.team.exeteamup.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwapRequest {
    @NotNull(message = "Vui lòng không để trống ID")
    private Long studentId1;

    @NotNull(message = "Vui lòng không để trống ID")
    private Long studentId2;
}
