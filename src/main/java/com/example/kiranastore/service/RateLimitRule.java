package com.example.kiranastore.service;

import java.time.Duration;

/**
 * The type Rate limit rule.
 */
public record RateLimitRule(int limit, Duration window) {
}
