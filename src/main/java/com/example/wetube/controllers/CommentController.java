package com.example.wetube.controllers;

import com.example.wetube.dto.CommentDto;
import com.example.wetube.entities.Comment;
import com.example.wetube.mappers.CommentMapper;
import com.example.wetube.services.CommentLikeService;
import com.example.wetube.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentLikeService likeService;

    @PostMapping("/videos/{videoId}/comments")
    public ResponseEntity<CommentDto> createComment(@AuthenticationPrincipal UserDetails user,
                                                    @RequestParam String text,
                                                    @PathVariable Long videoId) {
        Comment comment = commentService.createComment(text, videoId, user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(CommentMapper.toDto(comment, 0L));
    }

    @GetMapping("/videos/{videoId}/comments")
    public ResponseEntity<List<CommentDto>> getVideoComments(@PathVariable Long videoId) {
        List<Comment> comments = commentService.getVideoComments(videoId);
        List<CommentDto> commentsDto = comments.stream()
                .map(c -> CommentMapper.toDto(c, likeService.getVideoLikeCount(c.getId())))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
    }

    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<List<CommentDto>> getAllUserComments(@PathVariable Long userId) {
        List<Comment> comments = commentService.getAllUserComments(userId);
        List<CommentDto> commentsDto = comments.stream()
                .map(c -> CommentMapper.toDto(c, likeService.getVideoLikeCount(c.getId())))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
    }
}
