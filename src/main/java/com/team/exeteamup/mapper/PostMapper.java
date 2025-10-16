package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.request.PostRequest;
import com.team.exeteamup.dto.response.PostResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.Post;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.post.PostStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {
    public Post toEntity(PostRequest request, User user, Group group) {
        return Post.builder()
                .group(group)
                .user(user)
                .title(request.getTitle())
                .postDetail(request.getPostDetail())
                .postStatus(PostStatus.ACTIVE)
                .build();
    }

    public PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .groupId(post.getGroup().getGroupId())
                .userId(post.getUser().getUserId())
                .title(post.getTitle())
                .postDetail(post.getPostDetail())
                .postStatus(post.getPostStatus())
                .createdAt(post.getCreatedAt())
                .build();
        }

    public List<PostResponse> toResponseList(List<Post> posts) {
        return posts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
