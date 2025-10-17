package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.post.PostMajorRequest;
import com.team.exeteamup.entity.Post;
import com.team.exeteamup.entity.PostMajor;
import com.team.exeteamup.mapper.PostMajorMapper;
import com.team.exeteamup.repository.PostMajorRepository;
import com.team.exeteamup.service.PostMajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostMajorServiceImpl implements PostMajorService {

    private final PostMajorMapper postMajorMapper;
    private final PostMajorRepository postMajorRepository;

    @Override
    @Transactional
    public void savePostMajor(Post post, PostMajorRequest postMajorRequest) {
        PostMajor postMajor = postMajorMapper.toEntityFromRequest(post, postMajorRequest);
        PostMajor savedPostMajor = postMajorRepository.save(postMajor);
    }
}
