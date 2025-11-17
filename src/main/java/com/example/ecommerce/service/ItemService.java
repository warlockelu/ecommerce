package com.example.ecommerce.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.ecommerce.model.Item;
import com.example.ecommerce.repository.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository itemRepo;

    public ItemService(ItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }
}
