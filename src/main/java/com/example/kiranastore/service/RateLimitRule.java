package com.example.kiranastore.service;

import java.time.Duration;

public record RateLimitRule(int limit, Duration window) {
}
