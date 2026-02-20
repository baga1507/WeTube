package com.example.wetube.entities;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class VideoLikeId implements Serializable {
    private Long videoId;
    private Long userId;

    public VideoLikeId(Long videoId, Long userId) {
        this.videoId = videoId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoLikeId that)) return false;
        return Objects.equals(videoId, that.videoId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoId, userId);
    }
}
