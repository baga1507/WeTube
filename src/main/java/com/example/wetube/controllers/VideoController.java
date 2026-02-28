package com.example.wetube.controllers;

import com.example.wetube.dto.PaginatedRecommendationsDto;
import com.example.wetube.dto.VideoDto;
import com.example.wetube.services.RecommendationService;
import com.example.wetube.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;
    private final RecommendationService recommendationService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public VideoDto uploadVideo(@AuthenticationPrincipal UserDetails user,
                                                @RequestPart("file") MultipartFile file,
                                                @RequestPart("metadata") VideoUploadRequest metadata) {
        return videoService.uploadVideo(file, metadata.title, metadata.description, user.getUsername());
    }

    @GetMapping("/{id}")
    public VideoDto getVideoData(@PathVariable Long id) {
        return videoService.getVideoData(id);
    }

    @GetMapping("/{id}/stream")
    public String getVideoLink(@PathVariable Long id) {
        return videoService.getVideoLink(id);
    }

    @GetMapping
    public Page<VideoDto> getAllVideoData(Pageable pageable) {
        return videoService.getAllVideoData(pageable);
    }

    @GetMapping("/recommendations")
    public PaginatedRecommendationsDto getRecommendations(Pageable pageable,
                                                          @AuthenticationPrincipal UserDetails user) {
        return recommendationService.getRecommendations(user.getUsername(), pageable);
    }

    @PutMapping("/{id}/view")
    public void incrementViewCount(@PathVariable Long id) {
        videoService.incrementViewCount(id);
    }

    public record VideoUploadRequest(String title, String description) {}
}
