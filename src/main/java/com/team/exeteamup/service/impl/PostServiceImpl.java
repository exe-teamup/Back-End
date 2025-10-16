package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.PostRequest;
import com.team.exeteamup.dto.request.PostUpdateRequest;
import com.team.exeteamup.dto.response.PostResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.Post;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.post.PostStatus;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.mapper.PostMapper;
import com.team.exeteamup.repository.GroupRepository;
import com.team.exeteamup.repository.PostRepository;
import com.team.exeteamup.repository.StudentRepository;
import com.team.exeteamup.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        Group group = groupRepository.findById(postRequest.getGroupId())
                .orElseThrow(() -> new AppException("Không tìm thấy nhóm"));

        User user = studentRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new AppException("Không tìm thấy người dùng"));

        Post post = postMapper.toEntity(postRequest, user, group);
        postRepository.save(post);

        return postMapper.toResponse(post);
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
}
