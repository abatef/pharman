package com.abatef.pharman.controllers;

import com.abatef.pharman.dtos.employee.EmployeeCreationRequest;
import com.abatef.pharman.dtos.employee.EmployeeInfo;
import com.abatef.pharman.enums.EmployeeRole;
import com.abatef.pharman.exceptions.NonExistingEmpIdException;
import com.abatef.pharman.models.employee.EmpRoleId;
import com.abatef.pharman.models.employee.Employee;
import com.abatef.pharman.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emp")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/")
    public ResponseEntity<EmployeeInfo> createEmployee(@RequestBody EmployeeCreationRequest request,
                                                       @AuthenticationPrincipal Employee admin)
            throws NonExistingEmpIdException {
        EmployeeInfo info = employeeService.createEmployee(request);
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<List<EmployeeRole>> addRoleToEmployee(@PathVariable("id") Integer empId,
                                                                @RequestParam("role") EmployeeRole role)
            throws NonExistingEmpIdException {
        employeeService.addRoleToEmployee(empId, role);
        List<EmployeeRole> roles = employeeService.getEmployeeRoles(empId)
                .stream()
                .map(EmpRoleId::getRole)
                .toList();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}/role")
    public ResponseEntity<List<EmpRoleId>> getEmployeeRoles(@PathVariable("id") Integer empId) {
        List<EmpRoleId> roles = employeeService.getEmployeeRoles(empId);
        return ResponseEntity.ok(roles);
    }

    @DeleteMapping("/{id}/role")
    public ResponseEntity<List<EmployeeRole>> removeEmployeeRole(@PathVariable("id") Integer empId,
                                                                 @RequestParam("role") EmployeeRole role) {
        employeeService.removeRoleFromEmployee(empId, role);
        List<EmpRoleId> roles = employeeService.getEmployeeRoles(empId);
        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EmployeeRole> newRoles = roles.stream()
                .map(EmpRoleId::getRole).toList();
        return new ResponseEntity<>(newRoles, HttpStatus.OK);
    }

    @PatchMapping("/{id}/salary")
    public ResponseEntity<EmployeeInfo> updateEmployeeSalary(@PathVariable("id") Integer empId,
                                                             @RequestParam("salary") Float salary) {
        EmployeeInfo info = employeeService.updateEmployeeSalary(empId, salary);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeInfo> getEmployeeInfoById(@PathVariable("id") Integer empId) {
        EmployeeInfo info = employeeService.getEmployeeInfoById(empId);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping("/u/{username}")
    public ResponseEntity<EmployeeInfo> getEmployeeInfoByUsername(@PathVariable("username") String username) {
        EmployeeInfo info = employeeService.getEmployeeByUsername(username);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EmployeeInfo> updateEmployeeWorkStatus(@PathVariable("id") Integer empId,
                                                                 @RequestParam("status") String status) {
        EmployeeInfo info = employeeService.updateEmployeeWorkStatus(empId, status);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<EmployeeInfo> updateEmployeePassword(@PathVariable("id") Integer empId,
                                                               @RequestBody String password) {
        EmployeeInfo info = employeeService.updateEmployeePassword(empId, password);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

}
