package com.example.wetube.controllers;

import com.example.wetube.dto.VideoDto;
import com.example.wetube.entities.Video;
import com.example.wetube.mappers.VideoMapper;
import com.example.wetube.services.RecommendationService;
import com.example.wetube.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<VideoDto> uploadVideo(@AuthenticationPrincipal UserDetails user,
                                                @RequestPart("file") MultipartFile file,
                                                @RequestPart("metadata") VideoUploadRequest metadata) {
        Video createdVideo = videoService.uploadVideo(file, metadata.title, metadata.description, user.getUsername());
        VideoDto createdVideoDto = VideoMapper.toDto(createdVideo);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdVideoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getVideoData(@PathVariable Long id) {
        Video video = videoService.getVideoData(id);
        VideoDto videoDto = VideoMapper.toDto(video);

        return ResponseEntity.ok(videoDto);
    }

    @GetMapping("/{id}/stream")
    public ResponseEntity<String> getVideoLink(@PathVariable Long id) {
        String link = videoService.getVideoLink(id);

        return ResponseEntity.status(HttpStatus.OK).body(link);
    }

    @GetMapping
    public Page<VideoDto> getAllVideoData(Pageable pageable) {
        Page<Video> allVideoData = videoService.getAllVideoData(pageable);

        return allVideoData.map(VideoMapper::toDto);
    }

    @GetMapping("/recommendations")
    public Slice<VideoDto> getRecommendations(Pageable pageable,
                                              @AuthenticationPrincipal UserDetails user) {
        Slice<Video> recommendations = recommendationService.getRecommendations(user.getUsername(), pageable);

        return recommendations.map(VideoMapper::toDto);
    }

    @PutMapping("/{id}/view")
    public ResponseEntity<Void> incrementViewCount(@PathVariable Long id) {
        videoService.incrementViewCount(id);

        return ResponseEntity.ok().build();
    }

    public record VideoUploadRequest(String title, String description) {}
}
