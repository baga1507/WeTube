package com.example.wetube.controllers;

import com.example.wetube.dto.VideoDto;
import com.example.wetube.entities.Video;
import com.example.wetube.mappers.VideoMapper;
import com.example.wetube.services.VideoLikeService;
import com.example.wetube.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;
    private final VideoLikeService likeService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VideoDto> uploadVideo(@AuthenticationPrincipal UserDetails user,
                                                @RequestPart("file") MultipartFile file,
                                                @RequestPart("metadata") VideoUploadRequest metadata) {
        Video createdVideo = videoService.uploadVideo(file, metadata.title, metadata.description, user.getUsername());
        VideoDto createdVideoDto = VideoMapper.toDto(createdVideo, 0L);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdVideoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getVideoData(@PathVariable Long id) {
        Video video = videoService.getVideoData(id);
        Long likeCount = likeService.getVideoLikeCount(id);
        VideoDto videoDto = VideoMapper.toDto(video, likeCount);

        return ResponseEntity.ok(videoDto);
    }

    @GetMapping("/{id}/stream")
    public ResponseEntity<String> getVideoLink(@PathVariable Long id) {
        String link = videoService.getVideoLink(id);

        return ResponseEntity.status(HttpStatus.OK).body(link);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VideoDto>> getAllVideoData() {
        List<Video> allVideoData = videoService.getAllVideoData();
        List<VideoDto> allVideoDataDto = allVideoData.stream()
                .map(v -> VideoMapper.toDto(v, likeService.getVideoLikeCount(v.getId())))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(allVideoDataDto);
    }

    public record VideoUploadRequest(String title, String description) {}
}
