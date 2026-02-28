package com.example.wetube.controllers;

import com.example.wetube.dto.RecommendationPageDto;
import com.example.wetube.dto.VideoDto;
import com.example.wetube.services.RecommendationService;
import com.example.wetube.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;
    private final RecommendationService recommendationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public ResponseEntity<Void> stream(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(videoService.getVideoLink(id)))
                .build();
    }

    @GetMapping
    public Page<VideoDto> getAllVideoData(Pageable pageable) {
        return videoService.getAllVideoData(pageable);
    }

    @GetMapping("/recommendations")
    public RecommendationPageDto getRecommendations(Pageable pageable,
                                                    @AuthenticationPrincipal UserDetails user) {
        return recommendationService.getRecommendations(user.getUsername(), pageable);
    }

    @PostMapping("/{id}/views")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void incrementViewCount(@PathVariable Long id) {
        videoService.incrementViewCount(id);
    }

    public record VideoUploadRequest(String title, String description) {}
}
