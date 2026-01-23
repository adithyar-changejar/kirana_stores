package com.example.kiranastore.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Rate limit service.
 */
@Service
public class RateLimitService {

    private final StringRedisTemplate redisTemplate;

    /**
     * Instantiates a new Rate limit service.
     *
     * @param redisTemplate the redis template
     */
    public RateLimitService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Order matters (top → bottom)
    private static final Map<String, RateLimitRule> RULES = new LinkedHashMap<>();

    static {
        RULES.put("/health", new RateLimitRule(10, Duration.ofMinutes(1)));
        RULES.put("/actuator", new RateLimitRule(10, Duration.ofMinutes(1)));
        RULES.put("/auth", new RateLimitRule(20, Duration.ofMinutes(1)));
        RULES.put("/stores", new RateLimitRule(50, Duration.ofMinutes(1)));
        RULES.put("/cart", new RateLimitRule(10, Duration.ofMinutes(1)));
        RULES.put("/transactions", new RateLimitRule(5, Duration.ofMinutes(1)));
        RULES.put("/admin", new RateLimitRule(3, Duration.ofMinutes(1)));
    }

    /**
     * Is allowed boolean.
     *
     * @param key  the key
     * @param path the path
     * @return the boolean
     */
    public boolean isAllowed(String key, String path) {
        RateLimitRule rule = resolveRule(path);
        String redisKey = "rate_limit:" + path + ":" + key;

        Long count = redisTemplate.opsForValue().increment(redisKey);

        if (count != null && count == 1) {
            redisTemplate.expire(redisKey, rule.window());
        }

        return count != null && count <= rule.limit();
    }

    /**
     * Resolve rule rate limit rule.
     *
     * @param path the path
     * @return the rate limit rule
     */
    public RateLimitRule resolveRule(String path) {
        return RULES.entrySet().stream()
                .filter(e -> path.startsWith(e.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(new RateLimitRule(10, Duration.ofMinutes(1))); // default
    }
}
