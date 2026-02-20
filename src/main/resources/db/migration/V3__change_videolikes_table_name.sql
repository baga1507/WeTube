ALTER TABLE videolikes RENAME TO video_likes;
ALTER TABLE video_likes RENAME CONSTRAINT FK_VIDEOLIKES_ON_VIDEO TO fk_video_likes_video;
ALTER TABLE video_likes RENAME CONSTRAINT FK_VIDEOLIKES_ON_USER TO fk_video_likes_user;
ALTER INDEX videolikes_pkey RENAME TO video_likes_pkey;

