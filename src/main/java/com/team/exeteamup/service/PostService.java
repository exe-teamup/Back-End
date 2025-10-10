package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.PostRequest;
import com.team.exeteamup.dto.response.PostResponse;

public interface PostService {
    PostResponse createPost(PostRequest postRequest);
}
