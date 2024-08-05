package me.project.menu.service;

import me.project.menu.entity.Product;

import java.util.List;

public interface ProductService {
    Product add(Product newProduct);
    Product get(Long productId);
    List<Product> getAll();
    Product update(Product product);
}
