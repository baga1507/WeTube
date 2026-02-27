package com.example.wetube.dto;

import java.util.List;

public record PaginatedRecommendationsDto(
        List<VideoDto> videos,
        int page,
        int size,
        boolean hasNext
) { }
