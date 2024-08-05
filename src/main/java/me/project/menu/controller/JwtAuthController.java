package me.project.menu.controller;

import me.project.menu.entity.Customer;
import me.project.menu.security.CustomUserDetailsService;
import me.project.menu.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
public class JwtAuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("generate-token")
    public String generateToken(@RequestBody Customer customer) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(customer.getEmail());
        } else {
            throw new UsernameNotFoundException("Invalid User Request "+ customer.getEmail());
        }
    }
}
