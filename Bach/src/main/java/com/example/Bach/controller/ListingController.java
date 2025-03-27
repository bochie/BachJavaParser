package com.example.Bach.controller;

import com.example.Bach.model.List;
import com.example.Bach.service.ListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListingController {
    private final ListService listingService;

    public ListingController(ListService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/listings")
    public String getAllListings(Model model) {
        java.util.List<List> listings = listingService.getAllListings();
        model.addAttribute("listings", listings);
        return "listings"; // Назва HTML-файлу (listings.html)
    }
}
