package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product create(Product product);
    Product edit(UUID productId, Product product);
    List<Product> findAll();
    Product findOne(UUID productId);
}