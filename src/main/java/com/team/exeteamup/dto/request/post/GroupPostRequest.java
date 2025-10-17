package com.team.exeteamup.dto.request.post;

import com.team.exeteamup.enums.post.PostStatus;
import com.team.exeteamup.enums.post.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupPostRequest {
    private Long userId;
    private Long groupId;
    private String title;
    private String postDetail;
    private PostStatus postStatus;
    private LocalDateTime createdAt;
    private List<PostMajorRequest> postMajorRequests;
}
