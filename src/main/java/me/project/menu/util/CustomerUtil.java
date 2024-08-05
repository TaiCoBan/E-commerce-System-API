package me.project.menu.util;

import me.project.menu.entity.Customer;
import me.project.menu.exception.NotFoundException;
import me.project.menu.repository.CustomerRepo;
import me.project.menu.service.impl.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerUtil {

    private static final Logger log = LoggerFactory.getLogger(CustomerUtil.class);

    private static CustomerRepo customerRepo;

    @Autowired
    public CustomerUtil(CustomerRepo repo) {
        CustomerUtil.customerRepo = repo;
    }

    @Cacheable(value = "Customer", key = "'currentCustomer'")
    public Customer getCurrentCustomer() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("\n\nAuthentication: {}\n", authentication);

            if (authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();

                if (principal instanceof UserDetails) {
                    Optional<Customer> customer = customerRepo.findByEmail(((UserDetails) principal).getUsername());
                    return customer.orElseThrow(() -> new NotFoundException("Customer not found with the given username"));
                }
            }
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
        }

        return null;
    }

}
