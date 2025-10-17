package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.request.post.PostMajorRequest;
import com.team.exeteamup.dto.response.post.PostMajorResponse;
import com.team.exeteamup.entity.Post;
import com.team.exeteamup.entity.PostMajor;
import com.team.exeteamup.service.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMajorMapper {

    private final MajorService majorService;

    public PostMajorResponse toResponse(PostMajor postMajor) {
        return PostMajorResponse.builder()
                .postId(postMajor.getPost().getPostId())
                .majorId(postMajor.getMajor().getMajorId())
                .studentNum(postMajor.getStudentNum())
                .build();
    }

    public PostMajor toEntityFromRequest(Post post, PostMajorRequest postMajorRequest) {
        return PostMajor.builder()
                .post(post)
                .major(majorService.findById(postMajorRequest.getMajorId()))
                .studentNum(postMajorRequest.getStudentNum())
                .build();
    }

    public List<PostMajorResponse> toResponseList(List<PostMajor> postMajors) {
        return postMajors.stream()
                .map(this::toResponse)
                .toList();
    }
}
