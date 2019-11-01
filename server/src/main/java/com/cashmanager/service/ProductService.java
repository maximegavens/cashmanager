package com.cashmanager.service;

import com.cashmanager.model.Product;
import com.cashmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    public List<Product> getAllProduct() {
        List<Product> list_product = repository.findAll();
        if (list_product.size() > 0)
            return list_product;
        else
            return new ArrayList<Product>();
    }

    public Product getProductById(long id) {
        Optional<Product> prod = repository.findById(id);
        return prod.get(); //please add exception
    }

    public String createProduct(Product pr) {
        if (!repository.existsProductByName(pr.getName())) {
            Product prod = new Product();
            prod.setName(pr.getName());
            prod.setPrice(pr.getPrice());
            repository.save(prod);
            return "Success creating";
        }
        return "Error: This user already exists";
    }

    public void deleteProduct(Long id) {
        Optional<Product> prod = repository.findById(id);
        if (prod.isPresent())
            repository.deleteById(id);
    }
}
