package com.cashmanager.controller;

import com.cashmanager.model.Product;
import com.cashmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stock")
public class Management {

    @Autowired
    ProductService service;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product prod) {
        Product p = service.createProduct(prod);
        if (p != null) {
            System.out.println(p.getName());
            System.out.println(p.getPrice());
            System.out.println(p.getId_product());
            return new ResponseEntity<Product>(p, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<Product>(p, new HttpHeaders(), HttpStatus.FORBIDDEN); 
    }

    @PutMapping("/{id}")
    public boolean updateProduct(@PathVariable("id") long id, @RequestBody Product prod) {
        return service.UpdateProduct(id, prod);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") long id) {
        Product p = service.deleteProduct(id);
        if (p != null) {
            return new ResponseEntity<Product>(p, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<Product>(p, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
}
