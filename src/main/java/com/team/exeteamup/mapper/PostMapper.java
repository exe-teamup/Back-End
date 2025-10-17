package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.request.post.GroupPostRequest;
import com.team.exeteamup.dto.request.post.UserPostRequest;
import com.team.exeteamup.dto.response.post.GroupPostResponse;
import com.team.exeteamup.dto.response.post.PostResponse;
import com.team.exeteamup.dto.response.post.UserPostResponse;
import com.team.exeteamup.entity.Post;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.post.PostStatus;
import com.team.exeteamup.enums.post.PostType;
import com.team.exeteamup.service.GroupService;
import com.team.exeteamup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserService userService;
    private final PostMajorMapper postMajorMapper;
    private final GroupService groupService;

    public Post toEntity(GroupPostRequest request) {
        return Post.builder()
                .title(request.getTitle())
                .postDetail(request.getPostDetail())
                .postStatus(PostStatus.ACTIVE)
                .postType(PostType.GROUP_POST)
                .user(userService.findById(request.getUserId()))
                .group(groupService.findGroupById(request.getGroupId()))
                .build();
    }

    public Post toEntity(UserPostRequest request) {
        return Post.builder()
                .title(request.getTitle())
                .postDetail(request.getPostDetail())
                .postStatus(PostStatus.ACTIVE)
                .postType(PostType.USER_POST)
                .user(userService.findById(request.getUserId()))
                .group(null)
                .build();
    }


    public PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .postDetail(post.getPostDetail())
                .postStatus(post.getPostStatus())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getUserId())
                .postType(post.getPostType())
                .groupId(post.getGroup() != null ? post.getGroup().getGroupId() : null)
                .build();
    }

    public GroupPostResponse toGroupPostResponse(Post post) {
        return GroupPostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .postDetail(post.getPostDetail())
                .postStatus(post.getPostStatus())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getUserId())
                .groupId(post.getGroup().getGroupId())
                .postType(post.getPostType())
                .build();
    }

    public UserPostResponse toUserPostResponse(Post post) {
        return UserPostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .postDetail(post.getPostDetail())
                .postStatus(post.getPostStatus())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getUserId())
                .postType(post.getPostType())
                .build();
    }

    public List<PostResponse> toResponseList(List<Post> posts) {
        return posts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
