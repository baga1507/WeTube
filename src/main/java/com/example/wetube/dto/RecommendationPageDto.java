package com.example.wetube.dto;

import java.util.List;

public record RecommendationPageDto(
        List<VideoDto> videos,
        int page,
        int size,
        boolean hasNext
) { }
