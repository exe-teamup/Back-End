package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.post.GroupPostRequest;
import com.team.exeteamup.dto.request.post.PostUpdateRequest;
import com.team.exeteamup.dto.request.post.UserPostRequest;
import com.team.exeteamup.dto.response.post.GroupPostResponse;
import com.team.exeteamup.dto.response.post.PostResponse;
import com.team.exeteamup.dto.response.post.UserPostResponse;
import com.team.exeteamup.entity.Post;
import com.team.exeteamup.enums.post.PostStatus;

import java.util.List;

public interface PostService {
    GroupPostResponse createGroupPost(GroupPostRequest groupPostRequest);
    UserPostResponse createUserPost(UserPostRequest userPostRequest);
    List<PostResponse> getAllPosts();
    PostResponse updatePost(Long id, PostUpdateRequest request);
    void deletePost(Long id);
    Post findById(Long postId);
    List<PostResponse> getPostsByGroupId(Long groupId, PostStatus postStatus);
    PostResponse getPostById(Long id);
}
