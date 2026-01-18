package com.inventory.controller;

import com.inventory.model.Product;
import com.inventory.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты для ProductController
 */
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product("Товар 1", 10, 100.0);
        Product product2 = new Product("Товар 2", 20, 200.0);
        
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Товар 1"))
                .andExpect(jsonPath("$[1].name").value("Товар 2"));
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = new Product("Тестовый товар", 5, 50.0);
        product.setId(1L);
        
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Тестовый товар"))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product("Новый товар", 15, 150.0);
        product.setId(1L);
        
        when(productService.saveProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Новый товар"))
                .andExpect(jsonPath("$.quantity").value(15));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Product product = new Product("Товар для удаления", 1, 10.0);
        product.setId(1L);
        
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}