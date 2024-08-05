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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public Product update(Product product) {
        return null;
    }
}
