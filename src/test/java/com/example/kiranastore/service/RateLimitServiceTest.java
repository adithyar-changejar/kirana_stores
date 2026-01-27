package com.example.kiranastore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for RateLimitService
 */
@ExtendWith(MockitoExtension.class)
class RateLimitServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOps;

    /**
     * Allow request when under rate limit
     */
    @Test
    void shouldAllowRequestWithinLimit() {

        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.increment(anyString())).thenReturn(1L);

        RateLimitService service =
                new RateLimitService(redisTemplate);

        boolean allowed =
                service.isAllowed("user-1", "API_CART_ADD");

        assertTrue(allowed);
    }

    /**
     * Block request after exceeding limit
     */
    @Test
    void shouldBlockRequestAfterLimit() {

        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.increment(anyString())).thenReturn(11L); // limit = 10

        RateLimitService service =
                new RateLimitService(redisTemplate);

        boolean allowed =
                service.isAllowed("user-1", "API_CART_ADD");

        assertFalse(allowed);
    }
}
