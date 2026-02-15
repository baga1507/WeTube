package com.example.wetube.dto;

public record CommentDto(Long id, String text, VideoDto videoDto, UserDto userDto) {
}
