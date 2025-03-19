package com.abatef.pharman.controllers;

import com.abatef.pharman.dtos.employee.EmployeeCreationRequest;
import com.abatef.pharman.dtos.employee.EmployeeInfo;
import com.abatef.pharman.enums.EmployeeRole;
import com.abatef.pharman.exceptions.NonExistingEmpIdException;
import com.abatef.pharman.exceptions.handlers.MainExceptionHandler;
import com.abatef.pharman.models.employee.EmpRoleId;
import com.abatef.pharman.models.employee.Employee;
import com.abatef.pharman.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "create a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created new employee and return the new employee's info",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeInfo.class))}),
            @ApiResponse(responseCode = "409", description = "username is already used",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MainExceptionHandler.ErrorResponse.class))),
    })
    @PostMapping("/")
    public ResponseEntity<EmployeeInfo> createEmployee(@RequestBody EmployeeCreationRequest request,
                                                       @AuthenticationPrincipal Employee admin)
            throws NonExistingEmpIdException {
        EmployeeInfo info = employeeService.createEmployee(request);
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    @Operation(summary = "add a role to an existing employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "role added successfully and returned the list of user roles after adding the new role",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ArrayList.class))}),
            @ApiResponse(responseCode = "404", description = "the id does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MainExceptionHandler.ErrorResponse.class))),
    })
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


    @Operation(summary = "get employees role list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return the employee's role list",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmpRoleId.class))}),
            @ApiResponse(responseCode = "404", description = "the id does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MainExceptionHandler.ErrorResponse.class))),
    })
    @GetMapping("/{id}/role")
    public ResponseEntity<List<EmpRoleId>> getEmployeeRoles(@PathVariable("id") Integer empId) {
        List<EmpRoleId> roles = employeeService.getEmployeeRoles(empId);
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "remove a role from employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "removed employee's role successfully and return the list of roles after removing the role",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmpRoleId.class))}),
            @ApiResponse(responseCode = "404", description = "the id does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MainExceptionHandler.ErrorResponse.class))),
    })
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


    @Operation(summary = "update the employee's salary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "updated the salary and return the employee info after being updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeInfo.class))}),
            @ApiResponse(responseCode = "404", description = "the id does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MainExceptionHandler.ErrorResponse.class))),
    })
    @PatchMapping("/{id}/salary")
    public ResponseEntity<EmployeeInfo> updateEmployeeSalary(@PathVariable("id") Integer empId,
                                                             @RequestParam("salary") Float salary) {
        EmployeeInfo info = employeeService.updateEmployeeSalary(empId, salary);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @Operation(summary = "get the full employee info by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "the full employee info is returned",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeInfo.class))}),
            @ApiResponse(responseCode = "404", description = "the id does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MainExceptionHandler.ErrorResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeInfo> getEmployeeInfoById(@PathVariable("id") Integer empId) {
        EmployeeInfo info = employeeService.getEmployeeInfoById(empId);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @Operation(summary = "get the full employee info by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "the full employee info is returned",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeInfo.class))}),
            @ApiResponse(responseCode = "404", description = "the username does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MainExceptionHandler.ErrorResponse.class))),
    })
    @GetMapping("/u/{username}")
    public ResponseEntity<EmployeeInfo> getEmployeeInfoByUsername(@PathVariable("username") String username) {
        EmployeeInfo info = employeeService.getEmployeeByUsername(username);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }


    @Operation(summary = "update the employee's work status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "updated the status and return the employee info after being updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeInfo.class))}),
            @ApiResponse(responseCode = "404", description = "the id does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MainExceptionHandler.ErrorResponse.class))),
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<EmployeeInfo> updateEmployeeWorkStatus(@PathVariable("id") Integer empId,
                                                                 @RequestParam("status") String status) {
        EmployeeInfo info = employeeService.updateEmployeeWorkStatus(empId, status);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }


    @Operation(summary = "update the employee's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "updated the password and return the employee info after being updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeInfo.class))}),
            @ApiResponse(responseCode = "404", description = "the id does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MainExceptionHandler.ErrorResponse.class))),
    })
    @PatchMapping("/{id}/password")
    public ResponseEntity<EmployeeInfo> updateEmployeePassword(@PathVariable("id") Integer empId,
                                                               @RequestBody String password) {
        EmployeeInfo info = employeeService.updateEmployeePassword(empId, password);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

}
