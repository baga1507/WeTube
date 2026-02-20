package com.example.wetube.repositories;

import com.example.wetube.entities.CommentLike;
import com.example.wetube.entities.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {
    Long countByCommentId(Long commentId);
}
