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

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody Product product) {
        return ResponseEntity.ok(productService.add(product));
    }

    @GetMapping("{productId}")
    public ResponseEntity<?> get(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.get(productId));
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PutMapping("{productId}")
    public ResponseEntity<?> update(@RequestBody Product product,
                                    @PathVariable Long productId) {
        return ResponseEntity.ok(productService.update(product, productId));
    }
}
