package com.orderManagement.assignment.controller;

import com.orderManagement.assignment.model.Customer;
import com.orderManagement.assignment.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping
    public List<Customer> getAll() { return customerRepo.findAll(); }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer c) {
        return new ResponseEntity<>(customerRepo.save(c), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable Long id) {
        return customerRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer c) {
        return customerRepo.findById(id).map(existing -> {
            existing.setName(c.getName());
            existing.setEmail(c.getEmail());
            existing.setAddress(c.getAddress());
            existing.setPhone(c.getPhone());
            return new ResponseEntity<>(customerRepo.save(existing), HttpStatus.OK);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!customerRepo.existsById(id)) return ResponseEntity.notFound().build();
        customerRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
