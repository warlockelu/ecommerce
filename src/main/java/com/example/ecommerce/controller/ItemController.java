package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.ecommerce.model.Item;
import com.example.ecommerce.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> listItems() {
        return itemService.getAllItems();
    }
}
