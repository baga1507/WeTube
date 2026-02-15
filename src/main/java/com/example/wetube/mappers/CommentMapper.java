package com.example.wetube.mappers;

import com.example.wetube.dto.CommentDto;
import com.example.wetube.entities.Comment;

public class CommentMapper {
    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                VideoMapper.toDto(comment.getVideo()),
                UserMapper.toDto(comment.getUser())
        );
    }
}
