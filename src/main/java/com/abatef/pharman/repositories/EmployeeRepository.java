package com.abatef.pharman.repositories;

import com.abatef.pharman.models.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByUsername(String username);

    Optional<Employee> getEmployeeById(Integer id);

    Optional<Employee> getEmployeeByUsername(String username);
}
