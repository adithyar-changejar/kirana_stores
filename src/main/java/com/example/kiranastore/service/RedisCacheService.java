package com.example.kiranastore.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * The type Redis cache service.
 */
@Service
public class RedisCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Instantiates a new Redis cache service.
     *
     * @param redisTemplate the redis template
     */
    public RedisCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Set.
     *
     * @param key        the key
     * @param value      the value
     * @param ttlSeconds the ttl seconds
     */
    public void set(String key, Object value, long ttlSeconds) {
        redisTemplate.opsForValue()
                .set(key, value, Duration.ofSeconds(ttlSeconds));
    }

    /**
     * Get object.
     *
     * @param key the key
     * @return the object
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Delete.
     *
     * @param key the key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
