CREATE TABLE comment_likes (
    comment_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (comment_id, user_id),
    CONSTRAINT fk_comment_likes_on_comment FOREIGN KEY (comment_id) REFERENCES comments (id),
    CONSTRAINT fk_comment_likes_on_user FOREIGN KEY (user_id) REFERENCES users (id)
)