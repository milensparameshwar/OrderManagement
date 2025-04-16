package com.orderManagement.assignment.controller;

import com.orderManagement.assignment.model.GroceryItem;
import com.orderManagement.assignment.repository.GroceryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class GroceryItemController {
    @Autowired
    private GroceryItemRepository itemRepo;

    @GetMapping
    public List<GroceryItem> getAll() { return itemRepo.findAll(); }

    @PostMapping
    public ResponseEntity<GroceryItem> create(@RequestBody GroceryItem i) {
        return new ResponseEntity<>(itemRepo.save(i), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroceryItem> get(@PathVariable Long id) {
        return itemRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroceryItem> update(@PathVariable Long id, @RequestBody GroceryItem i) {
        return itemRepo.findById(id).map(existing -> {
            existing.setName(i.getName());
            existing.setCategory(i.getCategory());
            existing.setPrice(i.getPrice());
            existing.setQuantity(i.getQuantity());
            return new ResponseEntity<>(itemRepo.save(existing), HttpStatus.OK);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!itemRepo.existsById(id)) return ResponseEntity.notFound().build();
        itemRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

