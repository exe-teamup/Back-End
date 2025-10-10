package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.PostRequest;
import com.team.exeteamup.dto.request.PostUpdateRequest;
import com.team.exeteamup.dto.response.PostResponse;
import com.team.exeteamup.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.createPost(postRequest);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("group/{groupId}")
    public ResponseEntity<List<PostResponse>> getPostsByGroupId(@PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(postService.getPostsByGroupId(groupId));
    }

    @GetMapping("trash/{groupId}")
    public ResponseEntity<List<PostResponse>> getPostsInTrashByGroupId(@PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(postService.getPostsInTrashByGroup(groupId));
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PutMapping("{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable("id") Long id,
                                                   @RequestBody PostUpdateRequest request) {
        PostResponse response = postService.updatePost(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Xóa bài viết thành công");
    }
}