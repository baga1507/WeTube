package com.example.wetube.mappers;

import com.example.wetube.dto.UserDto;
import com.example.wetube.entities.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername());
    }
}
