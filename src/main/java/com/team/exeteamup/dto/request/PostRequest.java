package com.team.exeteamup.dto.request;

import com.team.exeteamup.enums.post.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long groupId;
    private String title;
    private String postDetail;
    private PostStatus postStatus;
    private LocalDateTime createdAt;
}
