package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.ecommerce.model.Item;

public interface ItemRepository extends MongoRepository<Item, String> {
}
