package com.example.wetube.controllers;

import com.example.wetube.services.VideoLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VideoLikeController {
    private final VideoLikeService videoLikeService;

    @PostMapping("/videos/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public void like(@AuthenticationPrincipal UserDetails user,
                                  @PathVariable Long videoId) {
        videoLikeService.like(videoId, user.getUsername());
    }

    @GetMapping("/videos/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkLike(@AuthenticationPrincipal UserDetails user,
                             @PathVariable Long videoId) {
        return videoLikeService.checkLike(videoId, user.getUsername());
    }

    @DeleteMapping("/videos/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public void unlike(@AuthenticationPrincipal UserDetails user,
                                     @PathVariable Long videoId) {
        videoLikeService.unlike(videoId, user.getUsername());
    }

    @GetMapping("/videos/{videoId}/likes")
    public Long getVideoLikeCount(@PathVariable Long videoId) {
        return videoLikeService.getVideoLikeCount(videoId);
    }
}
