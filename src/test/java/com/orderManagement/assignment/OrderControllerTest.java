package com.orderManagement.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderManagement.assignment.model.Customer;
import com.orderManagement.assignment.model.GroceryItem;
import com.orderManagement.assignment.model.Order;
import com.orderManagement.assignment.repository.CustomerRepository;
import com.orderManagement.assignment.repository.GroceryItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private CustomerRepository customerRepo;
    @Autowired private GroceryItemRepository itemRepo;

    @Test
    void testCreateOrder() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setName("John");
        customer.setEmail("john@example.com");
        customer.setAddress("Block A");
        customer.setPhone("9876543210");
        customer = customerRepo.save(customer);

        GroceryItem item = new GroceryItem();
        item.setName("Rice");
        item.setCategory("Grains");
        item.setPrice(40.0);
        item.setQuantity(2);
        item = itemRepo.save(item);

        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(List.of(item));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.totalPrice").value(40.0));
    }

    @Test
    void testGetOrderNotFound() throws Exception {
        mockMvc.perform(get("/api/orders/99999"))
                .andExpect(status().isNotFound());
    }
}

