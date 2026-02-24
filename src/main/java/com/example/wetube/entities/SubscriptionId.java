package com.example.wetube.entities;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class SubscriptionId implements Serializable {
    private Long subscriberId;
    private Long channelId;

    public SubscriptionId(Long subscriberId, Long channelId) {
        this.subscriberId = subscriberId;
        this.channelId = channelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriptionId that)) return false;
        return Objects.equals(subscriberId, that.subscriberId) &&
                Objects.equals(channelId, that.channelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriberId, channelId);
    }
}
