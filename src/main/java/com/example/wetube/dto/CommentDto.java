package com.example.wetube.dto;

public record CommentDto(Long id, String text, Long videoId, UserDto userDto) {
}
