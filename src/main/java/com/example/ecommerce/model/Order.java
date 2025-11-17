package com.example.ecommerce.model;

import java.util.List;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

@Table("orders")
public class Order {

    @PrimaryKey
    private String id;
    private String username;
    private List<String> itemIds;
    private double total;
    private String status;

    public Order() {
    }

    public Order(String id, String username, List<String> itemIds, double total, String status) {
        this.id = id;
        this.username = username;
        this.itemIds = itemIds;
        this.total = total;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getItemIds() {
        return itemIds;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public void setItemIds(List<String> itemIds) {
        this.itemIds = itemIds;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
