package com.cashmanager.controller;

import com.cashmanager.model.Product;
import com.cashmanager.repository.ProductRepository;
import com.cashmanager.service.ProductService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeTest {

    @Autowired
    ProductService service;

    @MockBean
    ProductRepository repository;

    @Test
    @DisplayName("Getter of all product")
    public void getAllProductTest() {
        when(repository.findAll()).thenReturn(Stream
            .of(new Product(), new Product()).collect(Collectors.toList()));
        assertEquals(2, service.getAllProduct().size());
    }

    @Test
    @DisplayName("Getter of a product by his Id")
    public void getProductByIdTest() {
        long id = 3;
        Product prod = new Product();
        prod.setPrice(1.50);
        prod.setName("Chocolate");
        when(repository.findById(id)).thenReturn(java.util.Optional.of(prod));
        assertEquals("Ketchup", service.getProductById(id).getName());
    }
}
