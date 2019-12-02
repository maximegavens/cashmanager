package com.cashmanager.controller;

import com.cashmanager.model.Product;
import com.cashmanager.service.ProductService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market")
public class Home {

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


    @GetMapping("/total")
    public ResponseEntity<Object> getTotal() {
        List<Product> list_prod = service.getAllProduct();
        Double total = 0.0;
        JSONObject json = new JSONObject();
        for(Product product: list_prod) {
            total = total + product.getPrice();
        }
        System.out.println(total);
        json.put("total", total.toString());
        return new ResponseEntity<Object>(json, new HttpHeaders(), HttpStatus.OK);
    }
}
