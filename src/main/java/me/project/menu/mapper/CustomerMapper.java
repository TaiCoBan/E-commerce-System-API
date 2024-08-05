package me.project.menu.mapper;

import me.project.menu.dto.CustomerDto;
import me.project.menu.entity.Customer;
import me.project.menu.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

//    @Autowired
//    private CustomerRepo customerRepo;

    public CustomerDto toDto(Customer customer) {
        return new CustomerDto(customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber());
    }
}
