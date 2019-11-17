package com.cashmanager.controller;

import com.cashmanager.model.Product;
import com.cashmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stock")
public class Management {

    @Autowired
    ProductService service;

    @PostMapping
    public String createProduct(@RequestBody Product prod) {
        return service.createProduct(prod);
    }

    @PutMapping("/{id}")
    public boolean updateProduct(@PathVariable("id") long id, @RequestBody Product prod) {
        return service.UpdateProduct(id, prod);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteProduct(@PathVariable("id") long id) {
        service.deleteProduct(id);
        return HttpStatus.FORBIDDEN;
    }
}
