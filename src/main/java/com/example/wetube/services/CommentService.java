package com.example.wetube.services;

import com.example.wetube.entities.Comment;
import com.example.wetube.entities.User;
import com.example.wetube.entities.Video;
import com.example.wetube.exceptions.UserNotFoundException;
import com.example.wetube.exceptions.VideoNotFoundException;
import com.example.wetube.repositories.CommentRepository;
import com.example.wetube.repositories.UserRepository;
import com.example.wetube.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment createComment(String text, Long videoId, String username) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Comment comment = new Comment();
        comment.setText(text);
        comment.setVideo(video);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    public List<Comment> getVideoComments(Long videoId) {
        return commentRepository.findAllByVideoId(videoId);
    }

    public List<Comment> getAllUserComments(Long userId) {
        return commentRepository.findAllByUserId(userId);
    }
}
