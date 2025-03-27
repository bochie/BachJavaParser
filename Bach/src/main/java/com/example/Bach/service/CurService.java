package com.example.Bach.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurService {
    private static final String API_URL = "https://www.olx.ua/uk/elektronika/telefony-i-aksesuary/mobilnye-telefony-smartfony/?currency=UAH";

    private final RestTemplate restTemplate;

    public CurService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("usdRate") // Кешуємо курс, щоб не робити багато запитів
    public BigDecimal getUsdRate() {
        Map[] response = restTemplate.getForObject(API_URL, Map[].class);
        if (response != null) {
            for (Map<String, String> currency : response) {
                if ("USD".equals(currency.get("ccy"))) {
                    return new BigDecimal(currency.get("sale"));
                }
            }
        }
        return BigDecimal.ZERO; // Якщо не знайдено
    }
}
