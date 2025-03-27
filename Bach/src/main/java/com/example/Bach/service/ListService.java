package com.example.Bach.service;

import com.example.Bach.model.List;
import com.example.Bach.repository.ListRepository;
import org.springframework.stereotype.Service;

@Service
public class ListService {
    private final ListRepository listingRepository;

    public ListService(ListRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public java.util.List<List> getAllListings() {
        return listingRepository.findAll();
    }

}
