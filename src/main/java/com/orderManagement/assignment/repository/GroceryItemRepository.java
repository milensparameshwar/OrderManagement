package com.orderManagement.assignment.repository;

import com.orderManagement.assignment.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {}
