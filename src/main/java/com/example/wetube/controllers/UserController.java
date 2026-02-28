package com.example.wetube.controllers;

import com.example.wetube.dto.UserDto;
import com.example.wetube.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@RequestBody RegisterRequest registerRequest) {
        return userService.registerUser(registerRequest.username, registerRequest.password);
    }

    @PostMapping("/login")
    public String login(@AuthenticationPrincipal UserDetails user) {
        return "User is logged in: " + user.getUsername();
    }

    public record RegisterRequest(String username, String password) {}
}
