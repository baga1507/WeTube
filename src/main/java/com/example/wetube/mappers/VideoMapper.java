package com.example.wetube.mappers;

import com.example.wetube.dto.VideoDto;
import com.example.wetube.entities.Video;

public class VideoMapper {
    public static VideoDto toDto(Video video, Long likeCounter) {
        return new VideoDto(
                video.getId(),
                video.getTitle(),
                video.getDescription(),
                UserMapper.toDto(video.getUser()),
                likeCounter
        );
    }
}
