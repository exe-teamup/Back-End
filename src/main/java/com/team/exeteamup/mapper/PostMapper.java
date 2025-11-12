package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.request.post.GroupPostRequest;
import com.team.exeteamup.dto.request.post.UserPostRequest;
import com.team.exeteamup.dto.response.post.GroupPostResponse;
import com.team.exeteamup.dto.response.post.PostResponse;
import com.team.exeteamup.dto.response.post.UserPostResponse;
import com.team.exeteamup.entity.Post;
import com.team.exeteamup.entity.PostMajor;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.post.PostStatus;
import com.team.exeteamup.enums.post.PostType;
import com.team.exeteamup.service.inter.GroupService;
import com.team.exeteamup.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserService userService;
    private final PostMajorMapper postMajorMapper;
    private final GroupService groupService;

    public Post toEntity(GroupPostRequest request) {
        Post post = new Post();

        List<PostMajor> postMajors = request.getPostMajorRequests().stream()
                .map(req -> {
                    PostMajor postMajor = postMajorMapper.toEntity(req);
                    postMajor.setPost(post);
                    return postMajor;
                }).toList();

        post.setTitle(request.getTitle());
        post.setPostDetail(request.getPostDetail());
        post.setPostStatus(PostStatus.ACTIVE);
        post.setPostType(PostType.GROUP_POST);
        post.setUser(userService.findById(request.getUserId()));
        post.setGroup(groupService.findGroupById(request.getGroupId()));
        post.setPostMajors(postMajors);

        return post;
    }

    public Post toEntity(UserPostRequest request, User user ) {
        return Post.builder()
                .title(request.getTitle())
                .postDetail(request.getPostDetail())
                .postStatus(PostStatus.ACTIVE)
                .postType(PostType.USER_POST)
                .user(user)
                .group(null)
                .createdAt(LocalDateTime.now())
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
                .authorName(post.getUser().getFullName())
                .groupId(post.getGroup().getGroupId())
                .postType(post.getPostType())
                .postMajors(post.getPostMajors() != null ?
                        postMajorMapper.toResponseList(post.getPostMajors()) : null)
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
