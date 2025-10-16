package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.post.GroupPostRequest;
import com.team.exeteamup.dto.request.post.PostUpdateRequest;
import com.team.exeteamup.dto.response.post.GroupPostResponse;
import com.team.exeteamup.dto.response.post.PostResponse;
import com.team.exeteamup.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/group-post")
    public ResponseEntity<GroupPostResponse> createGroupPost(@RequestBody GroupPostRequest groupPostRequest) {
        GroupPostResponse groupPostResponse = postService.createGroupPost(groupPostRequest);
        return ResponseEntity.ok(groupPostResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

//    @GetMapping("group/{groupId}")
//    public ResponseEntity<List<PostResponse>> getPostsByGroupId(@PathVariable("groupId") Long groupId) {
//        return ResponseEntity.ok(postService.getPostsByGroupId(groupId));
//    }

//    @GetMapping("trash/{groupId}")
//    public ResponseEntity<List<PostResponse>> getPostsInTrashByGroupId(@PathVariable("groupId") Long groupId) {
//        return ResponseEntity.ok(postService.getPostsInTrashByGroup(groupId));
//    }

//    @GetMapping("{postId}")
//    public ResponseEntity<PostResponse> getPostById(@PathVariable("postId") Long postId) {
//        return ResponseEntity.ok(postService.getPostById(postId));
//    }

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