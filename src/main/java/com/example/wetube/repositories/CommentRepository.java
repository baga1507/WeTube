package com.example.wetube.repositories;

import com.example.wetube.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByVideoId(Long id);
    List<Comment> findAllByUserId(Long id);
}
