package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.post.GroupPostRequest;
import com.team.exeteamup.dto.request.post.PostUpdateRequest;
import com.team.exeteamup.dto.response.post.GroupPostResponse;
import com.team.exeteamup.dto.response.post.PostResponse;
import com.team.exeteamup.entity.Post;

import java.util.List;

public interface PostService {
    GroupPostResponse createGroupPost(GroupPostRequest groupPostRequest);
    List<PostResponse> getAllPosts();
//    List<PostResponse> getPostsByGroupId(Long id);
//    List<PostResponse> getPostsInTrashByGroup(Long id);
//    PostResponse getPostById(Long id);
    PostResponse updatePost(Long id, PostUpdateRequest request);
    void deletePost(Long id);
    Post findById(Long postId);
}
