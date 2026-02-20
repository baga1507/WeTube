package com.example.wetube.controllers;

import com.example.wetube.dto.VideoDto;
import com.example.wetube.entities.Video;
import com.example.wetube.mappers.UserMapper;
import com.example.wetube.mappers.VideoMapper;
import com.example.wetube.services.VideoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VideoDto> uploadVideo(Authentication auth,
                                                @RequestPart("file") MultipartFile file,
                                                @RequestPart("metadata") VideoUploadRequest metadata) {
        Video createdVideo = videoService.uploadVideo(file, metadata.title, metadata.description, auth.getName());
        VideoDto createdVideoDto = VideoMapper.toDto(createdVideo);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdVideoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getVideoLink(@PathVariable Long id) {
        String link = videoService.getVideoLink(id);

        return ResponseEntity.status(HttpStatus.OK).body(link);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VideoDto>> getAllVideoData() {
        List<Video> allVideoData = videoService.getAllVideoData();
        List<VideoDto> allVideoDataDto = allVideoData.stream().map(VideoMapper::toDto).toList();

        return ResponseEntity.status(HttpStatus.OK).body(allVideoDataDto);
    }

    public record VideoUploadRequest(String title, String description) {}
}
