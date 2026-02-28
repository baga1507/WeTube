package com.example.wetube.controllers;

import com.example.wetube.services.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @PostMapping("/comments/{commentId}/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public void like(@AuthenticationPrincipal UserDetails user,
                                     @PathVariable Long commentId) {
        commentLikeService.like(commentId, user.getUsername());
    }

    @GetMapping("comments/{commentId}/likes")
    public Long getVideoLikeCount(@PathVariable Long commentId) {
        return commentLikeService.getVideoLikeCount(commentId);
    }

    @DeleteMapping("/comments/{commentId}/likes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(@AuthenticationPrincipal UserDetails user,
                                       @PathVariable Long commentId) {
        commentLikeService.unlike(commentId, user.getUsername());
    }
}
