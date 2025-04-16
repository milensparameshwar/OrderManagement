package com.orderManagement.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderManagement.assignment.model.GroceryItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GroceryItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void testCreateGroceryItem() throws Exception {
        GroceryItem item = new GroceryItem();
        item.setName("Tomato");
        item.setCategory("Vegetable");
        item.setPrice(25.5);
        item.setQuantity(10);

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Tomato"));
    }

    @Test
    void testGetGroceryItemNotFound() throws Exception {
        mockMvc.perform(get("/api/items/99999"))
                .andExpect(status().isNotFound());
    }
}
