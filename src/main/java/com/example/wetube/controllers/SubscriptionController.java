package com.example.wetube.controllers;

import com.example.wetube.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/subscriptions/{channelId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribe(@AuthenticationPrincipal UserDetails user,
                                          @PathVariable Long channelId) {
        subscriptionService.subscribe(user.getUsername(), channelId);
    }

    @DeleteMapping("/subscriptions/{channelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsubscribe(@AuthenticationPrincipal UserDetails user,
                                            @PathVariable Long channelId) {
        subscriptionService.unsubscribe(user.getUsername(), channelId);
    }
}
