package me.project.menu.service.impl;

import me.project.menu.entity.Category;
import me.project.menu.entity.Product;
import me.project.menu.exception.InvalidParamException;
import me.project.menu.exception.NotFoundException;
import me.project.menu.repository.CategoryRepo;
import me.project.menu.repository.ProductRepo;
import me.project.menu.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.file.NotDirectoryException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public Product add(Product newProduct) {
        log.info("\n\nNew Product: {}\n", newProduct);

        Long categoryId = newProduct.getCategoryId();
        Optional<Category> category = categoryRepo.findById(categoryId);

        if (category.isEmpty()) {
            throw new InvalidParamException("Category is not exist");
        }

        return productRepo.save(newProduct);
    }

    @Override
    @Cacheable(value = "Product", key = "#productId")
    public Product get(Long productId) {
        Optional<Product> product = productRepo.findById(productId);

        if (product.isEmpty()) {
            throw new NotFoundException("Product Not Found");
        }

        return product.get();
    }

    @Override
    @Cacheable(value = "Product")
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @Override
    @CacheEvict(value = "Product", key = "#productId")
    @CachePut(value = "Product", key = "#productId")
    public Product update(Product product, Long productId) {
        log.info("\n\nNew Product : {}\n", product);

        Long categoryId = product.getCategoryId();
        if (categoryRepo.findById(categoryId).isEmpty()) {
            throw new NotFoundException("Category Not Found");
        }
        if (product.getId() != productId) {
            if (productRepo.findById(product.getId()).isPresent()) {
                throw new InvalidParamException("Product ID already exists");
            }
            throw new InvalidParamException("Invalid Product ID");
        }

        Optional<Product> currentProduct = productRepo.findById(productId);
        if (currentProduct.isEmpty()) {
            throw new NotFoundException("Product Not Found");
        }

        Product p = currentProduct.get();
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setCategoryId(product.getCategoryId());

        return productRepo.save(p);
    }
}
