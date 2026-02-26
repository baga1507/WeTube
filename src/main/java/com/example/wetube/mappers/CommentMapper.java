package com.example.wetube.mappers;

import com.example.wetube.dto.CommentDto;
import com.example.wetube.entities.Comment;

public class CommentMapper {
    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getVideo().getId(),
                UserMapper.toDto(comment.getUser()),
                comment.getLikeCount()
        );
    }
}
