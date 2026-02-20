package com.example.wetube.repositories;

import com.example.wetube.entities.VideoLike;
import com.example.wetube.entities.VideoLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoLikeRepository extends JpaRepository<VideoLike, VideoLikeId> {
    Long countByVideoId(Long videoId);
}
