package com.example.kiranastore.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurrencyConversionService {

    private static final String FX_API_URL =
            "https://api.fxratesapi.com/latest?base=USD";

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public BigDecimal getUsdToInrRate() {

        Map<String, Object> response =
                restTemplate.getForObject(FX_API_URL, Map.class);

        Map<String, Object> rates =
                (Map<String, Object>) response.get("rates");

        Double inrRate = (Double) rates.get("INR");

        return BigDecimal.valueOf(inrRate);
    }
}
