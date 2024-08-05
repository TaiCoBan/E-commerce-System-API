package me.project.menu.controller;

import me.project.menu.entity.Customer;
import me.project.menu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("add-new-customer")
    public ResponseEntity<?> add(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.add(customer));
    }

    @GetMapping("get")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(customerService.get());
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.update(customer));
    }
}
