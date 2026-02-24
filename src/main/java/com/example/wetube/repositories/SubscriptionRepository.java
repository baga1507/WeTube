package com.example.wetube.repositories;

import com.example.wetube.entities.Subscription;
import com.example.wetube.entities.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
}
