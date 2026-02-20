package com.example.wetube.controllers;

import com.example.wetube.services.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<Void> like(@AuthenticationPrincipal UserDetails user,
                                     @PathVariable Long commentId) {
        commentLikeService.like(commentId, user.getUsername());

        return ResponseEntity.ok().build();
    }

    @GetMapping("comments/{commentId}/likes")
    public ResponseEntity<Long> getVideoLikeCount(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentLikeService.getVideoLikeCount(commentId));
    }

    @DeleteMapping("/comments/{commentId}/likes")
    public ResponseEntity<Void> unlike(@AuthenticationPrincipal UserDetails user,
                                       @PathVariable Long commentId) {
        commentLikeService.unlike(commentId, user.getUsername());

        return ResponseEntity.ok().build();
    }
}
