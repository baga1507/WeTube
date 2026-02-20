CREATE TABLE videolikes (
    video_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (video_id, user_id),
    CONSTRAINT FK_VIDEOLIKES_ON_VIDEO FOREIGN KEY (video_id) REFERENCES videos (id),
    CONSTRAINT FK_VIDEOLIKES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id)
)
