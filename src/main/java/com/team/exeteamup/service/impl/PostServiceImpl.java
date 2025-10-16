package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.post.GroupPostRequest;
import com.team.exeteamup.dto.request.post.PostMajorRequest;
import com.team.exeteamup.dto.request.post.PostUpdateRequest;
import com.team.exeteamup.dto.response.post.GroupPostResponse;
import com.team.exeteamup.dto.response.post.PostMajorResponse;
import com.team.exeteamup.dto.response.post.PostResponse;
import com.team.exeteamup.entity.Post;
import com.team.exeteamup.entity.PostMajor;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.post.PostStatus;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.mapper.PostMajorMapper;
import com.team.exeteamup.mapper.PostMapper;
import com.team.exeteamup.repository.PostMajorRepository;
import com.team.exeteamup.repository.PostRepository;
import com.team.exeteamup.service.PostMajorService;
import com.team.exeteamup.service.PostService;
import com.team.exeteamup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;
    private final PostMajorService postMajorService;

    @Override
    @Transactional
    public GroupPostResponse createGroupPost(GroupPostRequest groupPostRequest) {

        endIfUserHasNoGroup(groupPostRequest.getUserId());

        Post post = postMapper.toEntity(groupPostRequest);
        Post savedPost = postRepository.save(post);

        List<PostMajorRequest> postMajorRequests = groupPostRequest.getPostMajorRequests();

        postMajorRequests.forEach(postMajorRequest -> {
            postMajorService.savePostMajor(savedPost, postMajorRequest);
        });

        return postMapper.toGroupPostResponse(savedPost);
    }


    private void endIfUserHasNoGroup(long userId) {
        User user = userService.findById(userId);
        if(user.getGroup() == null) {
            throw new AppException("User has no group");
        }
    }


    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findByPostStatus(PostStatus.ACTIVE);
        return postMapper.toResponseList(posts);
    }

//    @Override
//    public List<PostResponse> getPostsByGroupId(Long groupId) {
//        List<Post> post = postRepository.findByGroup_GroupIdAndPostStatus(groupId, PostStatus.ACTIVE);
//
//        if (post.isEmpty()) {
//            throw new AppException("Nhóm này chưa có bài viết nào");
//        }
//
//        List<Post> posts = postRepository.findByGroup_GroupIdAndPostStatus(groupId, PostStatus.ACTIVE);
//        return postMapper.toResponseList(posts);
//    }

//    @Override
//    public List<PostResponse> getPostsInTrashByGroup(Long id) {
//        List<Post> posts = postRepository.findByGroup_GroupIdAndPostStatus(id, PostStatus.TRASHED);
//        return postMapper.toResponseList(posts);
//    }

//    @Override
//    public PostResponse getPostById(Long id) {
//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new AppException("Không tìm thấy bài viết"));
//
//        return postMapper.toResponse(post);
//    }

    @Override
    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException("Không tìm thấy bài viết"));

        Optional.ofNullable(request.getTitle()).ifPresent(post::setTitle);
        Optional.ofNullable(request.getPostDetail()).ifPresent(post::setPostDetail);

        Optional.ofNullable(request.getPostStatus())
                .ifPresent(post::setPostStatus);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        return postMapper.toResponse(post);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException("Không tìm thấy bài viết"));

        switch (post.getPostStatus()) {
            case ACTIVE -> {
                post.setPostStatus(PostStatus.TRASHED);
                postRepository.save(post);
            }
            case TRASHED -> {
                post.setPostStatus(PostStatus.DELETED);
                postRepository.save(post);
            }
            case DELETED -> {
                throw new AppException("Bài viết đã bị xóa");
            }
        }
    }

    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new AppException("Không tìm thấy bài viết"));
    }
}
