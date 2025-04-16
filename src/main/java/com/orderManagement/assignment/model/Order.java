package com.orderManagement.assignment.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToMany
    private List<GroceryItem> items;

    private LocalDateTime orderDate;
    private double totalPrice;

    @PrePersist
    @PreUpdate
    public void calculateTotalPrice() {
        this.totalPrice = items.stream().mapToDouble(GroceryItem::getPrice).sum();
    }
}