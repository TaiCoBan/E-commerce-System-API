package me.project.menu.service.impl;

import me.project.menu.repository.ProductRepo;
import me.project.menu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;
}
