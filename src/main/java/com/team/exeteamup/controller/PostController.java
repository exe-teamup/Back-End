package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.PostRequest;
import com.team.exeteamup.dto.response.PostResponse;
import com.team.exeteamup.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pots")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.createPost(postRequest);
        return ResponseEntity.ok(postResponse);
    }
}
