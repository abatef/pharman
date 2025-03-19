package com.abatef.pharman.config;

import com.abatef.pharman.dtos.employee.EmployeeCreationRequest;
import com.abatef.pharman.dtos.employee.EmployeeInfo;
import com.abatef.pharman.models.employee.EmpRole;
import com.abatef.pharman.models.employee.Employee;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        ModelMapper rawModelMapper = new ModelMapper();
        Converter<Employee, EmployeeInfo> empToEmpInfoConverter = ctx -> {
            Employee employee = ctx.getSource();
            EmployeeInfo employeeInfo = rawModelMapper.map(employee, EmployeeInfo.class);
            employeeInfo.getRoles().clear();
            for (EmpRole empRole : employee.getEmpRoles()) {
                employeeInfo.getRoles().add(empRole.getId().getRole());
            }
            return employeeInfo;
        };
        modelMapper.createTypeMap(Employee.class, EmployeeInfo.class).setConverter(empToEmpInfoConverter);
        return modelMapper;
    }
}
