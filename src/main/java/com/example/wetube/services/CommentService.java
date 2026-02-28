package com.example.wetube.services;

import com.example.wetube.dto.CommentDto;
import com.example.wetube.entities.Comment;
import com.example.wetube.entities.User;
import com.example.wetube.entities.Video;
import com.example.wetube.exceptions.UserNotFoundException;
import com.example.wetube.exceptions.VideoNotFoundException;
import com.example.wetube.mappers.CommentMapper;
import com.example.wetube.repositories.CommentRepository;
import com.example.wetube.repositories.UserRepository;
import com.example.wetube.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentDto createComment(String text, Long videoId, String username) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Comment comment = new Comment();
        comment.setText(text);
        comment.setVideo(video);
        comment.setUser(user);
        comment.setLikeCount(0L);

        return CommentMapper.toDto(commentRepository.save(comment));
    }

    public Slice<CommentDto> getVideoComments(Long videoId, Pageable pageable) {
        return commentRepository.findAllByVideoId(videoId, pageable).map(CommentMapper::toDto);
    }

    public Slice<CommentDto> getAllUserComments(Long userId, Pageable pageable) {
        return commentRepository.findAllByUserId(userId, pageable).map(CommentMapper::toDto);
    }
}
