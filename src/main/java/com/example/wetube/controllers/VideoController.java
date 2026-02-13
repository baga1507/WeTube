package com.example.wetube.controllers;

import com.example.wetube.dto.VideoDto;
import com.example.wetube.entities.Video;
import com.example.wetube.mappers.UserMapper;
import com.example.wetube.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;
    private final ObjectMapper mapper;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VideoDto> uploadVideo(Authentication auth,
                                                @RequestPart("file") MultipartFile file,
                                                @RequestPart("metadata") String metadata) {
        VideoUploadRequest uploadRequest = mapper.readValue(metadata, VideoUploadRequest.class);
        Video createdVideo = videoService.uploadVideo(file, uploadRequest.title, uploadRequest.description, auth.getName());
        VideoDto createdVideoDto = new VideoDto(
                createdVideo.getId(),
                createdVideo.getTitle(),
                createdVideo.getDescription(),
                UserMapper.toDto(createdVideo.getUser())
        );
        return new ResponseEntity<>(createdVideoDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public String getVideoLink(@PathVariable Long id) {
        return videoService.getVideoLink(id);
    }

    @GetMapping("/all")
    public List<Video> getAllVideoData() {
        return videoService.getAllVideoData();
    }

    public record VideoUploadRequest(String title, String description) {}
}
