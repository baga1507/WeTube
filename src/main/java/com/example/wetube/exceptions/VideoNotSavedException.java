package com.example.wetube.exceptions;

public class VideoNotSavedException extends RuntimeException {
    public VideoNotSavedException(String message) {
        super(message);
    }
}
