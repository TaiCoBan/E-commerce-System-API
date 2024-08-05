package me.project.menu.controller;

import me.project.menu.constants.Constant;
import me.project.menu.entity.Product;
import me.project.menu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("add-new-product")
    public ResponseEntity<?> add(@RequestBody Product product) {
        return ResponseEntity.ok(productService.add(product));
    }

    @GetMapping("get/{productId}")
    public ResponseEntity<?> get(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.get(productId));
    }
}
