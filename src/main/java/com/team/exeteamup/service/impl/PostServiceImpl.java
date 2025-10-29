package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.post.GroupPostRequest;
import com.team.exeteamup.dto.request.post.PostUpdateRequest;
import com.team.exeteamup.dto.request.post.UserPostRequest;
import com.team.exeteamup.dto.response.post.GroupPostResponse;
import com.team.exeteamup.dto.response.post.PostResponse;
import com.team.exeteamup.dto.response.post.UserPostResponse;
import com.team.exeteamup.entity.Post;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.post.PostStatus;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.mapper.PostMapper;
import com.team.exeteamup.repository.PostRepository;
import com.team.exeteamup.service.inter.PostService;
import com.team.exeteamup.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Override
    @Transactional
    public GroupPostResponse createGroupPost(GroupPostRequest groupPostRequest) {

        endIfUserHasNoGroup(groupPostRequest.getUserId());

        Post post = postMapper.toEntity(groupPostRequest);

        Post savedPost = postRepository.save(post);

        return postMapper.toGroupPostResponse(savedPost);
    }

    public List<GroupPostResponse> getGroupPosts() {
        List<Post> posts = postRepository.findByPostTypeAndPostStatus(
                com.team.exeteamup.enums.post.PostType.GROUP_POST,
                PostStatus.ACTIVE
        );
        return posts.stream().map(postMapper::toGroupPostResponse).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public UserPostResponse createUserPost(UserPostRequest userPostRequest) {

        Post post = postMapper.toEntity(userPostRequest);
        Post savedPost = postRepository.save(post);

        return postMapper.toUserPostResponse(savedPost);
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

    @Override
    public List<PostResponse> getPostsByGroupId(Long groupId, PostStatus postStatus) {
        List<Post> post = postRepository.findPostsByGroupIdAndPostStatus(groupId, postStatus);

        if (post.isEmpty()) {
            throw new AppException("Nhóm này chưa có bài viết nào");
        }
        List<Post> posts = postRepository.findPostsByGroupIdAndPostStatus(groupId, postStatus);
        return postMapper.toResponseList(posts);
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException("Không tìm thấy bài viết"));

        return postMapper.toResponse(post);
    }

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
