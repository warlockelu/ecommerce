package com.example.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.ecommerce.repository.ItemRepository;
import com.example.ecommerce.model.Item;

@SpringBootApplication
public class EcommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    // Seed some initial items into MongoDB at startup
    @Bean
    CommandLineRunner loadData(ItemRepository itemRepo) {
        return args -> {
            if (itemRepo.count() == 0) {
                itemRepo.save(new Item(
                        "Phone",
                        "Smartphone XYZ with 5G support, 128GB storage",
                        699.0
                ));

                itemRepo.save(new Item(
                        "Laptop",
                        "Ultrabook ABC, lightweight design, 16GB RAM",
                        1299.0
                ));

                itemRepo.save(new Item(
                        "Tablet",
                        "10-inch Media Tablet, Full HD, 64GB storage",
                        399.0
                ));

                itemRepo.save(new Item(
                        "Smartwatch",
                        "Fitness & Health Tracker, heart rate monitor",
                        199.0
                ));

                itemRepo.save(new Item(
                        "Headphones",
                        "Noise Cancelling Wireless Headphones",
                        149.0
                ));

                itemRepo.save(new Item(
                        "Gaming Mouse",
                        "RGB Ergonomic Gaming Mouse, 12000 DPI",
                        89.0
                ));
            }
        };
    }
}
