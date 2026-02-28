package com.example.wetube.controllers;

import com.example.wetube.dto.CommentDto;
import com.example.wetube.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/videos/{videoId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@AuthenticationPrincipal UserDetails user,
                                                    @RequestParam String text,
                                                    @PathVariable Long videoId) {
        return commentService.createComment(text, videoId, user.getUsername());
    }

    @GetMapping("/videos/{videoId}/comments")
    public Slice<CommentDto> getVideoComments(Pageable pageable,
                                              @PathVariable Long videoId) {
        return commentService.getVideoComments(videoId, pageable);
    }

    @GetMapping("/users/{userId}/comments")
    public Slice<CommentDto> getAllUserComments(Pageable pageable,
                                               @PathVariable Long userId) {
        return commentService.getAllUserComments(userId, pageable);
    }
}
