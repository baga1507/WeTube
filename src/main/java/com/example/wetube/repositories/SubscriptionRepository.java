package com.example.wetube.repositories;

import com.example.wetube.entities.Subscription;
import com.example.wetube.entities.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
    @Query("SELECT s.channel.id FROM subscriptions s WHERE s.subscriber.id = :userId")
    Set<Long> findSubscribedChannelIds(Long userId);
}
