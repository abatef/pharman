package com.abatef.pharman.services;


import com.abatef.pharman.exceptions.NonExistingUsernameException;
import com.abatef.pharman.models.employee.Employee;
import com.abatef.pharman.repositories.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MainUserDetailsService implements UserDetailsService {

    private final EmployeeService employeeService;

    public MainUserDetailsService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return employeeService.tryGetEmployeeByUsername(username);
        } catch (NonExistingUsernameException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
