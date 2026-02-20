package com.example.wetube.exceptions;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(Long id) {
        super("Video with id " + id + " not found");
    }
}
