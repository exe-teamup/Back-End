package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.request.PostRequest;
import com.team.exeteamup.dto.response.PostResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post toEntity(PostRequest request, Group group) {
        return Post.builder()
                .group(group)
                .title(request.getTitle())
                .postDetail(request.getPostDetail())
                .build();
    }

    public PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .postDetail(post.getPostDetail())
                .createdAt(post.getCreatedAt())
                .build();
        }
}
