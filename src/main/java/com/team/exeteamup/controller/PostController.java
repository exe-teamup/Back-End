package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.post.GroupPostRequest;
import com.team.exeteamup.dto.request.post.PostUpdateRequest;
import com.team.exeteamup.dto.request.post.UserPostRequest;
import com.team.exeteamup.dto.response.post.GroupPostResponse;
import com.team.exeteamup.dto.response.post.PostResponse;
import com.team.exeteamup.dto.response.post.UserPostResponse;
import com.team.exeteamup.enums.post.PostStatus;
import com.team.exeteamup.service.inter.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PostController {

    private final PostService postService;


    @PostMapping("/group-post")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<GroupPostResponse> createGroupPost(@RequestBody GroupPostRequest groupPostRequest) {
        GroupPostResponse groupPostResponse = postService.createGroupPost(groupPostRequest);
        return ResponseEntity.ok(groupPostResponse);
    }


    @GetMapping("/group-post")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'LECTURER', 'STUDENT'})")
    public ResponseEntity<List<GroupPostResponse>> getGroupPost() {
        List<GroupPostResponse> groupPostResponse = postService.getGroupPosts();
        return ResponseEntity.ok(groupPostResponse);
    }


    @PostMapping("/user-post")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<UserPostResponse> createUserPost(@RequestBody UserPostRequest userPostRequest) {
        UserPostResponse userPostResponse = postService.createUserPost(userPostRequest);
        return ResponseEntity.ok(userPostResponse);
    }


    @GetMapping("")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'LECTURER', 'STUDENT'})")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }


    @GetMapping("group/{groupId}")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'LECTURER', 'STUDENT'})")
    public ResponseEntity<List<PostResponse>> getPostsByGroupId(
            @PathVariable("groupId") Long groupId,
            @RequestParam PostStatus postStatus) {
        return ResponseEntity.ok(postService.getPostsByGroupId(groupId, postStatus));
    }


    @GetMapping("{postId}")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'LECTURER', 'STUDENT'})")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }


    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'STUDENT'})")
    public ResponseEntity<PostResponse> updatePost(@PathVariable("id") Long id,
                                                   @RequestBody PostUpdateRequest request) {
        PostResponse response = postService.updatePost(id, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority({'MODERATOR', 'ADMIN', 'STUDENT'})")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Xóa bài viết thành công");
    }
}