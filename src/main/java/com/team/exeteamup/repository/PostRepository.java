package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Post;
import com.team.exeteamup.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPostStatus(PostStatus postStatus);
    List<Post> findByGroup_GroupIdAndPostStatus(Long groupId, PostStatus postStatus);
}
