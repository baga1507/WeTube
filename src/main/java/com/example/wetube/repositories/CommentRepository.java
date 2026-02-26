package com.example.wetube.repositories;

import com.example.wetube.entities.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Slice<Comment> findAllByVideoId(Long videoId, Pageable pageable);
    Slice<Comment> findAllByUserId(Long userId, Pageable pageable);
}
