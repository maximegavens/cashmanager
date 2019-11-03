package com.cashmanager.service;

import com.cashmanager.model.Product;
import com.cashmanager.repository.ManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManagementService {

    @Autowired
    ManagementRepository repository;

    public String createProduct(Product pr) {
        if (!repository.existsProductByName(pr.getName())) {
            Product prod = new Product();
            prod.setName(pr.getName());
            prod.setPrice(pr.getPrice());
            repository.save(prod);
            return "Success creating";
        }
        return "Error: This product already exists";
    }

    public Boolean UpdateUser(Product pr) {
        if (repository.existsById(pr.getId_product())) {
            Product prod = repository.getOne(pr.getId_product());
            prod.setName(pr.getName());
            prod.setPrice(pr.getPrice());
            repository.save(prod);
            return true;
        }
        return false;
    }

    public void deleteProduct(Long id) {
        Optional<Product> prod = repository.findById(id);
        if (prod.isPresent())
            repository.deleteById(id);
    }
}
