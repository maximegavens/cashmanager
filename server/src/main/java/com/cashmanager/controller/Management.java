package com.cashmanager.controller;

import com.cashmanager.model.Product;
import com.cashmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/stock")
public class Management {

    @Autowired
    ProductService service;

    @PostMapping
    public ResponseEntity<List<Product>> createProduct(@RequestBody Product prod) {
        Product p = service.createProduct(prod);
        if (p != null) {
            List<Product> list_prod = service.getAllProduct();
            return new ResponseEntity<List<Product>>(list_prod, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<List<Product>>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @PutMapping("/{id}")
    public boolean updateProduct(@PathVariable("id") long id, @RequestBody Product prod) {
        return service.UpdateProduct(id, prod);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Product>> deleteProduct(@PathVariable("id") long id) {
        Product p = service.deleteProduct(id);
        if (p != null) {
            List<Product> list_prod = service.getAllProduct();
            return new ResponseEntity<List<Product>>(list_prod, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<List<Product>>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @DeleteMapping
    public ResponseEntity<List<Product>> deleteAllProduct() {
        service.deleteAllProduct();
        List<Product> list_prod = new ArrayList<Product>();
        return new ResponseEntity<List<Product>>(list_prod, new HttpHeaders(), HttpStatus.OK);
    }

}
