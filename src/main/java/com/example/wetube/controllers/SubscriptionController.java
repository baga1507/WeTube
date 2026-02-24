package com.example.wetube.controllers;

import com.example.wetube.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/subscriptions/{channelId}")
    public ResponseEntity<Void> subscribe(@AuthenticationPrincipal UserDetails user,
                                          @PathVariable Long channelId) {
        subscriptionService.subscribe(user.getUsername(), channelId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/subscriptions/{channelId}")
    public ResponseEntity<Void> unsubscribe(@AuthenticationPrincipal UserDetails user,
                                            @PathVariable Long channelId) {
        subscriptionService.unsubscribe(user.getUsername(), channelId);

        return ResponseEntity.ok().build();
    }
}
