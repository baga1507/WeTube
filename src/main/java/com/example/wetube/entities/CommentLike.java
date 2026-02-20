package com.example.wetube.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity(name = "comment_likes")
@NoArgsConstructor
public class CommentLike {
    @EmbeddedId
    private CommentLikeId id;

    @ManyToOne
    @MapsId("commentId")
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    public CommentLike(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
        this.id = new CommentLikeId(comment.getId(), user.getId());
    }
}
