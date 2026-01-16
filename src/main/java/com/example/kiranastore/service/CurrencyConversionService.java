package com.example.kiranastore.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

@Service
public class CurrencyConversionService {

    private static final String FX_API_URL =
            "https://api.fxratesapi.com/latest?base=USD";

    private static final String REDIS_KEY = "FX:USD:INR";
    private static final long TTL_SECONDS = 3600; // 1 hour

    private final RedisTemplate<String, Object> redisTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    public CurrencyConversionService(
            RedisTemplate<String, Object> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
    }

    @SuppressWarnings("unchecked")
    public BigDecimal getUsdToInrRate() {

        // Try Redis
        Object cachedRate = redisTemplate.opsForValue().get(REDIS_KEY);

        if (cachedRate != null) {
            System.out.println("FX rate loaded from Redis");
            return new BigDecimal(cachedRate.toString());
        }

        System.out.println("FX rate fetched from external API");


        // Call external API
        Map<String, Object> response =
                restTemplate.getForObject(FX_API_URL, Map.class);

        Map<String, Object> rates =
                (Map<String, Object>) response.get("rates");

        Double inrRate = (Double) rates.get("INR");

        BigDecimal rate = BigDecimal.valueOf(inrRate);

        // Store in Redis
        redisTemplate.opsForValue()
                .set(REDIS_KEY, rate, Duration.ofSeconds(TTL_SECONDS));

        return rate;
    }
}
