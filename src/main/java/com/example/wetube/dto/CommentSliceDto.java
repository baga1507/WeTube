package com.example.wetube.dto;

import java.util.List;

public record CommentSliceDto(
        List<CommentDto> comments,
        boolean hasNext
) {}
