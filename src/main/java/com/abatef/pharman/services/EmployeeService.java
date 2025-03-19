package com.abatef.pharman.services;

import com.abatef.pharman.dtos.employee.EmployeeCreationRequest;
import com.abatef.pharman.dtos.employee.EmployeeInfo;
import com.abatef.pharman.enums.EmployeeRole;
import com.abatef.pharman.exceptions.NonExistingEmpIdException;
import com.abatef.pharman.exceptions.NonExistingUsernameException;
import com.abatef.pharman.exceptions.NonUniqueUsernameException;
import com.abatef.pharman.models.employee.EmpRole;
import com.abatef.pharman.models.employee.EmpRoleId;
import com.abatef.pharman.models.employee.Employee;
import com.abatef.pharman.repositories.EmpRoleRepository;
import com.abatef.pharman.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmpRoleRepository empRoleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           EmpRoleRepository empRoleRepository,
                           ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.empRoleRepository = empRoleRepository;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public EmployeeInfo createEmployee(EmployeeCreationRequest request) throws NonUniqueUsernameException {
        Employee employee = modelMapper.map(request, Employee.class);
        boolean usernameExists = employeeRepository.existsByUsername(request.getUsername());
        if (usernameExists) {
            throw new NonUniqueUsernameException(request.getUsername());
        }
        employeeRepository.save(employee);

        return modelMapper.map(employee, EmployeeInfo.class);
    }

    protected Employee tryGetEmployeeById(Integer empId) throws NonExistingEmpIdException {
        Optional<Employee> employee = employeeRepository.getEmployeeById(empId);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new NonExistingEmpIdException(empId);
    }

    protected Employee tryGetEmployeeByUsername(String username) throws NonExistingUsernameException {
        Optional<Employee> employee = employeeRepository.getEmployeeByUsername(username);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new NonExistingUsernameException(username);
    }

    protected Boolean isEmployeeExist(Integer empId) {
        return employeeRepository.getEmployeeById(empId).isPresent();
    }

    protected void isEmployeeExistsOrThrow(Integer empId) {
        if (isEmployeeExist(empId)) {
            return;
        }
        throw new NonExistingEmpIdException(empId);
    }

    @Transactional
    public void addRoleToEmployee(Integer empId, EmployeeRole role) throws NonExistingEmpIdException {
        Employee employee = tryGetEmployeeById(empId);
        EmpRole empRole = EmpRole.of(employee, role);
        empRoleRepository.save(empRole);
    }

    @Transactional
    public void removeRoleFromEmployee(Integer empId, EmployeeRole role) throws NonExistingEmpIdException {
        EmpRole empRole = EmpRole.of(empId, role);
        empRoleRepository.delete(empRole);
    }

    public List<EmpRoleId> getEmployeeRoles(Integer empId) throws NonExistingEmpIdException {
        Employee employee = tryGetEmployeeById(empId);
        return employee.getEmpRoles().stream()
                .map(EmpRole::getId).toList();
    }

    @Transactional
    public EmployeeInfo updateEmployeeSalary(Integer empId, Float salary) throws NonExistingEmpIdException {
        Employee employee = tryGetEmployeeById(empId);
        employee.setSalary(salary);
        employee = employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeInfo.class);
    }

    public EmployeeInfo getEmployeeInfoById(Integer empId) throws NonExistingEmpIdException {
        Employee employee = tryGetEmployeeById(empId);
        return modelMapper.map(employee, EmployeeInfo.class);
    }

    public EmployeeInfo getEmployeeByUsername(String username) throws NonExistingUsernameException {
        Employee employee = tryGetEmployeeByUsername(username);
        return modelMapper.map(employee, EmployeeInfo.class);
    }

    @Transactional
    public EmployeeInfo updateEmployeeWorkStatus(Integer empId, String workStatus) throws NonExistingEmpIdException {
        Employee employee = tryGetEmployeeById(empId);
        employee.setWorkStatus(workStatus);
        employee = employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeInfo.class);
    }

    @Transactional
    public EmployeeInfo updateEmployeePassword(Integer empId, String password) throws NonExistingEmpIdException {
        Employee employee = tryGetEmployeeById(empId);
        employee.setPassword(password);
        employee = employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeInfo.class);
    }
}
