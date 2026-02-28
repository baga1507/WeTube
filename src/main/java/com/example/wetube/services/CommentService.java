package com.example.wetube.services;

import com.example.wetube.dto.CommentDto;
import com.example.wetube.dto.CommentSliceDto;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

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

        CommentDto commentDto = CommentMapper.toDto(commentRepository.save(comment));

        evictAllPage0CommentsOfVideo(videoId);
        evictAllPage0CommentsOfUser(user.getId());

        return commentDto;
    }

    @Cacheable(
            value = "VIDEO_COMMENT_CACHE",
            key = "#videoId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize",
            condition = "#pageable.pageNumber == 0"
    )
    public CommentSliceDto getVideoComments(Long videoId, Pageable pageable) {
        Slice<CommentDto> slice = commentRepository.findAllByVideoId(videoId, pageable).map(CommentMapper::toDto);

        return new CommentSliceDto(slice.getContent(), slice.hasNext());
    }

    @Cacheable(
            value = "USER_COMMENT_CACHE",
            key = "#userId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize",
            condition = "#pageable.pageNumber == 0"
    )
    public CommentSliceDto getAllUserComments(Long userId, Pageable pageable) {
        Slice<CommentDto> slice = commentRepository.findAllByUserId(userId, pageable).map(CommentMapper::toDto);

        return new CommentSliceDto(slice.getContent(), slice.hasNext());
    }

    private void evictAllPage0CommentsOfVideo(Long videoId) {
        Set<String> keys = redisTemplate.keys("VIDEO_COMMENT_CACHE::%d-0-*".formatted(videoId));

        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    private void evictAllPage0CommentsOfUser(Long userId) {
        Set<String> keys = redisTemplate.keys("USER_COMMENT_CACHE::%d-0-*".formatted(userId));

        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
