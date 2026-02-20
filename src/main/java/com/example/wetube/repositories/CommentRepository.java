package com.example.wetube.repositories;

import com.example.wetube.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByVideoId(Long videoId);
    List<Comment> findAllByUserId(Long userId);
}
