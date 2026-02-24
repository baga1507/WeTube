package com.example.wetube.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "subscriptions")
@NoArgsConstructor
@Getter
public class Subscription {
    @EmbeddedId
    private SubscriptionId id;

    @ManyToOne
    @MapsId("subscriberId")
    private User subscriber;

    @ManyToOne
    @MapsId("channelId")
    private User channel;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Subscription(User subscriber, User channel) {
        this.subscriber = subscriber;
        this.channel = channel;
        this.id = new SubscriptionId(subscriber.getId(), channel.getId());
    }
}
