package com.example.wetube.controllers;

import com.example.wetube.dto.CommentDto;
import com.example.wetube.entities.Comment;
import com.example.wetube.mappers.CommentMapper;
import com.example.wetube.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos/{videoId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(Authentication auth,
                                                    @RequestParam String text,
                                                    @PathVariable Long videoId) {
        Comment comment = commentService.createComment(text, videoId, auth.getName());

        return new ResponseEntity<>(CommentMapper.toDto(comment), HttpStatus.CREATED);
    }
}
