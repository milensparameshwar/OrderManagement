package com.orderManagement.assignment.repository;

import com.orderManagement.assignment.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
