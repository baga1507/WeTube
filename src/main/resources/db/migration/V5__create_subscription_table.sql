CREATE TABLE subscriptions (
    subscriber_id BIGINT NOT NULL,
    channel_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (subscriber_id, channel_id),
    CONSTRAINT fk_subscriptions_on_subscriber FOREIGN KEY (subscriber_id) REFERENCES users (id),
    CONSTRAINT fk_subscriptions_on_channel FOREIGN KEY (channel_id) REFERENCES users (id),
    CONSTRAINT chk_no_self_subscription CHECK (subscriber_id <> channel_id)
);

CREATE INDEX idx_subscriptions_channel_id
ON subscriptions(channel_id);
