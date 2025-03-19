package com.abatef.pharman.dtos.employee;

import com.abatef.pharman.enums.EmployeeRole;
import com.abatef.pharman.enums.Sex;
import com.abatef.pharman.models.employee.EmpRole;
import com.abatef.pharman.models.employee.EmpRoleId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfo {
    private Integer id;
    private String name;
    private String username;
    private Short age;
    private Sex sex;
    private Float salary;
    private String workStatus;
    private Instant createdAt;
    private Instant updatedAt;
    private List<EmployeeRole> roles = new ArrayList<>();
}
