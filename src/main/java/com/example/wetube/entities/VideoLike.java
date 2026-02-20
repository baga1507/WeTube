package com.example.wetube.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "videolikes")
@NoArgsConstructor
@Getter
@Setter
public class VideoLike {
    @EmbeddedId
    private VideoLikeId id;

    @ManyToOne
    @MapsId("videoId")
    @JoinColumn(name = "video_id")
    private Video video;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    public VideoLike(Video video, User user) {
        this.video = video;
        this.user = user;
        this.id = new VideoLikeId(video.getId(), user.getId());
    }
}
