package com.example.kiranastore.controller;

import com.example.kiranastore.mongo.StoreDocument;
import com.example.kiranastore.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Store controller.
 */
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * Gets stores.
     *
     * @return the stores
     */
    @GetMapping
    public List<StoreDocument> getStores() {
        return storeService.getActiveStores();
    }
}
