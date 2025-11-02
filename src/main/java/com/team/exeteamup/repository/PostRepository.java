package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Post;
import com.team.exeteamup.enums.post.PostStatus;
import com.team.exeteamup.enums.post.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPostStatus(PostStatus postStatus);

    @Query("""
    SELECT p FROM Post p
    WHERE p.group.groupId = :groupId
    AND p.postStatus = :status
""")
    List<Post> findPostsByGroupIdAndPostStatus(
            @Param("groupId") Long groupId,
            @Param("status") PostStatus postStatus);

    List<Post> findByPostTypeAndPostStatus(PostType postType, PostStatus postStatus);
    long countByCreatedAtAfter(LocalDateTime dateTime);
}
