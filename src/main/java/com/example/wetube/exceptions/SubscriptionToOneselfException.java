package com.example.wetube.exceptions;

public class SubscriptionToOneselfException extends RuntimeException {
    public SubscriptionToOneselfException() {
        super("Cannot subscribe to yourself");
    }
}
