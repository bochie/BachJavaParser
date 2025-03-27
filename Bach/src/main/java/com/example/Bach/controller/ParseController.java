package com.example.Bach.controller;

import com.example.Bach.service.ListParserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parse")
public class ParseController {
    private final ListParserService listingParserService;

    public ParseController(ListParserService listingParserService) {
        this.listingParserService = listingParserService;
    }

    @GetMapping
    public String triggerParsing() {
        listingParserService.parseListings(); // Викликаємо метод без параметрів
        return "Парсинг запущено!";
    }
}
