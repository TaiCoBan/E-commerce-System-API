package me.project.menu.service;

import me.project.menu.dto.CustomerDto;
import me.project.menu.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer add(Customer newCustomer);
    CustomerDto get();
    List<CustomerDto> getAll();
    Customer update(Customer customer);
}
