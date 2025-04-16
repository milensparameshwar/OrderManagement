package com.orderManagement.assignment.controller;

import com.orderManagement.assignment.model.Order;
import com.orderManagement.assignment.repository.CustomerRepository;
import com.orderManagement.assignment.repository.GroceryItemRepository;
import com.orderManagement.assignment.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private GroceryItemRepository itemRepo;

    @GetMapping
    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.calculateTotalPrice();
        return new ResponseEntity<>(orderRepo.save(order), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable Long id) {
        return orderRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!orderRepo.existsById(id)) return ResponseEntity.notFound().build();
        orderRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
