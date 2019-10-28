package com.cashmanager.controller;

import com.cashmanager.model.Product;
import com.cashmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market")
public class main_page {

    @Autowired
    ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> list_prod = service.getAllProduct();
        return new ResponseEntity<List<Product>>(list_prod, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        Product prod = service.getProductById(id);
        return new ResponseEntity<Product>(prod, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct( Product us ) {
        Product prod = service.createProduct(us);
        return new ResponseEntity<Product>(prod, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteProduct(@PathVariable("id") long id) {
        service.deleteProduct(id);
        return HttpStatus.FORBIDDEN;
    }
}
