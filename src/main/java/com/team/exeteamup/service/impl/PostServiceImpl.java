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
import com.team.exeteamup.enums.post.PostType;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.mapper.PostMapper;
import com.team.exeteamup.repository.PostRepository;
import com.team.exeteamup.service.inter.PostService;
import com.team.exeteamup.service.inter.UserService;
import com.team.exeteamup.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    private final UserUtils userUtils;

    @Override
    @Transactional
    @CacheEvict(cacheNames = "group_posts", allEntries = true)
    public GroupPostResponse createGroupPost(GroupPostRequest groupPostRequest) {

        User user = userUtils.getCurrentUser();

        endIfUserHasNoGroup(user);

        Post post = postMapper.toEntity(groupPostRequest, user);

        Post savedPost = postRepository.save(post);

        return postMapper.toGroupPostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    @Cacheable("group_posts")
    public List<GroupPostResponse> getGroupPosts() {
        List<Post> posts = postRepository.findByPostTypeAndPostStatus(PostType.GROUP_POST, PostStatus.ACTIVE);
        return posts.stream()
                .map(postMapper::toGroupPostResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "group_posts", allEntries = true)
    public GroupPostResponse updateGroupPost(Long id, GroupPostRequest request) {
        return null;
    }


    @Override
    @Transactional
    @CacheEvict(cacheNames = "user_posts", allEntries = true)
    public UserPostResponse createUserPost(UserPostRequest userPostRequest) {
        User user = userUtils.getCurrentUser();
        Post post = postMapper.toEntity(userPostRequest, user);
        Post savedPost = postRepository.save(post);

        return postMapper.toUserPostResponse(savedPost);
    }

    @Override
    @Cacheable("user_posts")
    public List<UserPostResponse> getUserPosts() {
        List<Post> posts = postRepository.findByPostTypeAndPostStatus(PostType.USER_POST, PostStatus.ACTIVE);
        return posts.stream()
                .map(postMapper::toUserPostResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "user_posts", allEntries = true)
    public UserPostResponse updateUserPost(Long id, UserPostRequest request) {
        return null;
    }


    private void endIfUserHasNoGroup(User user) {
        if(user.getGroup() == null) {
            throw new AppException("User has no group");
        }
    }

    @Override
    @Cacheable("posts")
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findByPostStatus(PostStatus.ACTIVE);
        return postMapper.toResponseList(posts);
    }

    @Override
    //@Cacheable(value = "posts_by_group", key = "#groupId + '_' + #postStatus")
    public List<PostResponse> getPostsByGroupId(Long groupId, PostStatus postStatus) {
        List<Post> post = postRepository.findPostsByGroupIdAndPostStatus(groupId, postStatus);

        if (post.isEmpty()) {
            throw new AppException("Nhóm này chưa có bài viết nào");
        }
        List<Post> posts = postRepository.findPostsByGroupIdAndPostStatus(groupId, postStatus);
        return postMapper.toResponseList(posts);
    }

    @Override
    //@Cacheable(value = "post", key = "#id")
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException("Không tìm thấy bài viết"));

        return postMapper.toResponse(post);
    }

    @Override
    @CacheEvict(cacheNames = "post", allEntries = true)
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
    @CacheEvict(cacheNames = "post", allEntries = true)
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
    @Cacheable(value = "post", key = "#postId")
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new AppException("Không tìm thấy bài viết"));
    }
}
