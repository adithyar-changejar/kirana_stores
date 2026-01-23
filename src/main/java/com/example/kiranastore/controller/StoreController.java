package com.example.kiranastore.controller;

import com.example.kiranastore.mongo.StoreDocument;
import com.example.kiranastore.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public List<StoreDocument> getStores() {
        return storeService.getActiveStores();
    }
}
