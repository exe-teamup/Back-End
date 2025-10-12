package com.team.exeteamup.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.exeteamup.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private Long groupId;
    private String title;
    private String postDetail;
    private PostStatus postStatus;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdAt;
}
