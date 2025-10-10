package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
