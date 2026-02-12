package com.example.wetube.controllers;

import com.example.wetube.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;

    @PostMapping("/upload")
    public void uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            videoService.uploadVideo(file);
        } catch (IOException e) {
            System.out.println("Couldn't upload the video");
        }
    }

    @GetMapping("/{id}")
    public String getVideoLink(@PathVariable Long id) {
        return videoService.getVideoLink(id);
    }
}
