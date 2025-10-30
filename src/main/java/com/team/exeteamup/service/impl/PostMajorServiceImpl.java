package com.team.exeteamup.service.impl;

import com.team.exeteamup.service.inter.PostMajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostMajorServiceImpl implements PostMajorService {

}
