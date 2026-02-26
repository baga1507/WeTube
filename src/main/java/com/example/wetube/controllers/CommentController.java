package com.example.wetube.controllers;

import com.example.wetube.dto.CommentDto;
import com.example.wetube.entities.Comment;
import com.example.wetube.mappers.CommentMapper;
import com.example.wetube.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/videos/{videoId}/comments")
    public ResponseEntity<CommentDto> createComment(@AuthenticationPrincipal UserDetails user,
                                                    @RequestParam String text,
                                                    @PathVariable Long videoId) {
        Comment comment = commentService.createComment(text, videoId, user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(CommentMapper.toDto(comment));
    }

    @GetMapping("/videos/{videoId}/comments")
    public Slice<CommentDto> getVideoComments(Pageable pageable,
                                              @PathVariable Long videoId) {
        Slice<Comment> comments = commentService.getVideoComments(videoId, pageable);

        return comments.map(CommentMapper::toDto);
    }

    @GetMapping("/users/{userId}/comments")
    public Slice<CommentDto> getAllUserComments(Pageable pageable,
                                               @PathVariable Long userId) {
        Slice<Comment> comments = commentService.getAllUserComments(userId, pageable);

        return comments.map(CommentMapper::toDto);
    }
}
