package com.example.wetube.controllers;

import com.example.wetube.services.VideoLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class VideoLikeController {
    private final VideoLikeService videoLikeService;

    @PostMapping("/videos/{videoId}/likes")
    public ResponseEntity<Void> like(@AuthenticationPrincipal UserDetails user,
                                  @PathVariable Long videoId) {
        videoLikeService.like(videoId, user.getUsername());

        return ResponseEntity.ok().build();
    }

    @GetMapping("videos/{videId}/likes")
    public ResponseEntity<Long> getVideoLikeCount(@PathVariable Long videId) {
        return ResponseEntity.ok(videoLikeService.getVideoLikeCount(videId));
    }
}
