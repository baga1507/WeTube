package com.example.wetube.controllers;

import com.example.wetube.services.VideoLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class VideoLikeController {
    private final VideoLikeService videoLikeService;

    @PostMapping("/videos/{videoId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public void like(@AuthenticationPrincipal UserDetails user,
                                  @PathVariable Long videoId) {
        videoLikeService.like(videoId, user.getUsername());
    }

    @GetMapping("/videos/{videoId}/likes")
    public Long getVideoLikeCount(@PathVariable Long videoId) {
        return videoLikeService.getVideoLikeCount(videoId);
    }

    @DeleteMapping("/videos/{videoId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public void unlike(@AuthenticationPrincipal UserDetails user,
                                     @PathVariable Long videoId) {
        videoLikeService.unlike(videoId, user.getUsername());
    }
}
