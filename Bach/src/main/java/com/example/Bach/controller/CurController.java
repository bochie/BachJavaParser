package com.example.Bach.controller;

import com.example.Bach.service.CurService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/currency")
public class CurController {
    private final CurService currencyService;

    public CurController(CurService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/usd")
    public BigDecimal getUsdRate() {
        return currencyService.getUsdRate();
    }
}
