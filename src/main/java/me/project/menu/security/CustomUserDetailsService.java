package me.project.menu.security;

import me.project.menu.entity.Customer;
import me.project.menu.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepo repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> customer = repo.findByEmail(email);

        return customer.map(cus -> new CustomUserDetails(cus))
                .orElseThrow(() -> new UsernameNotFoundException("Customer Not Found"));
    }
}
