package com.example.wetube.services;

import com.example.wetube.entities.Subscription;
import com.example.wetube.entities.SubscriptionId;
import com.example.wetube.entities.User;
import com.example.wetube.exceptions.SubscriptionToOneselfException;
import com.example.wetube.exceptions.UserNotFoundException;
import com.example.wetube.repositories.SubscriptionRepository;
import com.example.wetube.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public void subscribe(String subscriberUsername, Long channelId) {
        User subscriber = userRepository.findByUsername(subscriberUsername)
                .orElseThrow(() -> new UserNotFoundException(subscriberUsername));
        User channel = userRepository.findById(channelId)
                .orElseThrow(() -> new UserNotFoundException(channelId));

        if (subscriber.getId().equals(channel.getId())) {
            throw new SubscriptionToOneselfException();
        }

        SubscriptionId id = new SubscriptionId(subscriber.getId(), channel.getId());

        if (subscriptionRepository.existsById(id)) {
            return;
        }

        Subscription subscription = new Subscription(subscriber, channel);
        subscriptionRepository.save(subscription);
    }

    public void unsubscribe(String subscriberUsername, Long channelId) {
        User subscriber = userRepository.findByUsername(subscriberUsername)
                .orElseThrow(() -> new UserNotFoundException(subscriberUsername));
        User channel = userRepository.findById(channelId)
                .orElseThrow(() -> new UserNotFoundException(channelId));

        SubscriptionId id = new SubscriptionId(subscriber.getId(), channel.getId());

        subscriptionRepository.deleteById(id);
    }
}
