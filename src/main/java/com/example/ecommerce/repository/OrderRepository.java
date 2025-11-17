package com.example.ecommerce.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import com.example.ecommerce.model.Order;

public interface OrderRepository extends CassandraRepository<Order, String> {
}
