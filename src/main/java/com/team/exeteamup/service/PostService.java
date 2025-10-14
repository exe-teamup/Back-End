package com.team.exeteamup.service;

import com.mysql.cj.log.Log;
import com.team.exeteamup.dto.request.PostRequest;
import com.team.exeteamup.dto.request.PostUpdateRequest;
import com.team.exeteamup.dto.response.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest postRequest);
    List<PostResponse> getAllPosts();
//    List<PostResponse> getPostsByGroupId(Long id);
//    List<PostResponse> getPostsInTrashByGroup(Long id);
//    PostResponse getPostById(Long id);
    PostResponse updatePost(Long id, PostUpdateRequest request);
    void deletePost(Long id);
}
