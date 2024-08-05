package me.project.menu.service.impl;

import me.project.menu.dto.CustomerDto;
import me.project.menu.entity.Customer;
import me.project.menu.exception.InvalidParamException;
import me.project.menu.exception.NotFoundException;
import me.project.menu.mapper.CustomerMapper;
import me.project.menu.repository.CustomerRepo;
import me.project.menu.service.CustomerService;
import me.project.menu.util.CustomerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerUtil customerUtil;

    @Override
    public Customer add(Customer newCustomer) {
        log.info("\n\nNew Customer: {}\n", newCustomer);

        if (newCustomer.getEmail() == null || newCustomer.getPassword() == null) {
            throw new InvalidParamException("Email or password can not be null");
        }

        String email = newCustomer.getEmail();
        if (customerRepo.findByEmail(email).isPresent()) {
            throw new InvalidParamException("This email already exists");
        }

        newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        return customerRepo.save(newCustomer);
    }

    @Override
    @Cacheable(value = "Customer", key = "'currentCustomer'")
    public CustomerDto get() {
        Customer currentCus = customerUtil.getCurrentCustomer();
        log.info("\n\nCurrent customer: {}\n", currentCus);

        if (currentCus == null) {
            throw new NotFoundException("Customer Not Found");
        }

        return customerMapper.toDto(Objects.requireNonNull(currentCus));
    }

    @Override
    @Cacheable("Customer")
    public List<CustomerDto> getAll() {
        return customerRepo.findAll().stream().map(customer -> customerMapper.toDto(customer)).collect(Collectors.toList());
    }

    @Override
    public Customer update(Customer customer) {
        Customer currentCus = customerUtil.getCurrentCustomer();
        log.info("\n\nCurrent customer: {}\n", currentCus);

        if (currentCus == null) {
            throw new NotFoundException("Customer Not Found");
        }

        currentCus.setFirstName(customer.getFirstName());
        currentCus.setLastName(customer.getLastName());

        if (customer.getEmail() != null &&
                !customer.getEmail().equals(currentCus.getEmail()) &&
                customerRepo.findByEmail(currentCus.getEmail()).isPresent()) {
            throw new InvalidParamException("This email already exists");
        } else if (customer.getEmail() != null) {
            currentCus.setEmail(customer.getEmail());
        }

        if (customer.getPassword() != null) {
            currentCus.setPassword(passwordEncoder.encode(customer.getPassword()));
        }

        currentCus.setAddress(customer.getAddress());
        currentCus.setPhoneNumber(customer.getPhoneNumber());

        if (currentCus.getEmail() == null || currentCus.getPassword() == null) {
            throw new InvalidParamException("Email or password can not be null");
        }

        return customerRepo.save(currentCus);
    }
}
