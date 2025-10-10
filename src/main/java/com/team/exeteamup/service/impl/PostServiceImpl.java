package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.PostRequest;
import com.team.exeteamup.dto.response.PostResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.Post;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.mapper.PostMapper;
import com.team.exeteamup.repository.GroupRepository;
import com.team.exeteamup.repository.PostRepository;
import com.team.exeteamup.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        Group group = groupRepository.findById(postRequest.getGroupId())
                .orElseThrow(() -> new AppException("Không tìm thấy nhóm"));

        Post post = postMapper.toEntity(postRequest, group);
        postRepository.save(post);

        return postMapper.toResponse(post);
    }
}
