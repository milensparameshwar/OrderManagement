package com.orderManagement.assignment.repository;

import com.orderManagement.assignment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
