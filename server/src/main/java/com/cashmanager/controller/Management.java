package com.cashmanager.controller;

import com.cashmanager.model.Product;
import com.cashmanager.service.ManagementService;
import com.cashmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class Management {

    @Autowired
    ManagementService service;

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> list_prod = productService.getAllProduct();
        return new ResponseEntity<List<Product>>(list_prod, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public String createProduct(@RequestBody Product prod) {
        return service.createProduct(prod);
    }

    @PostMapping("/edit")
    public boolean updateUser(@RequestBody Product prod) {
        return service.UpdateUser(prod);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteProduct(@PathVariable("id") long id) {
        service.deleteProduct(id);
        return HttpStatus.FORBIDDEN;
    }
}
