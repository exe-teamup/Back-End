package com.team.exeteamup.dto.response.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.exeteamup.enums.post.PostStatus;
import com.team.exeteamup.enums.post.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupPostResponse {
    private Long postId;
    private Long userId;
    private Long groupId;
    private String title;
    private String postDetail;
    private PostStatus postStatus;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdAt;
    private PostType postType;
}
