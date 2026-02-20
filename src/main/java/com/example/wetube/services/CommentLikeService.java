package com.example.wetube.services;

import com.example.wetube.entities.*;
import com.example.wetube.exceptions.UserNotFoundException;
import com.example.wetube.exceptions.VideoNotFoundException;
import com.example.wetube.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void like(Long commentId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new VideoNotFoundException(commentId));

        CommentLikeId id = new CommentLikeId(comment.getId(), user.getId());

        if (commentLikeRepository.existsById(id)) {
            return;
        }

        CommentLike videoLike = new CommentLike(comment, user);
        commentLikeRepository.save(videoLike);
    }

    @Transactional
    public void unlike(Long videoId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        Comment comment = commentRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));

        CommentLikeId id = new CommentLikeId(comment.getId(), user.getId());

        commentLikeRepository.deleteById(id);
    }

    public Long getVideoLikeCount(Long videoId) {
        return commentLikeRepository.countByCommentId(videoId);
    }
}
