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

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostMajorServiceImpl implements PostMajorService {

}
