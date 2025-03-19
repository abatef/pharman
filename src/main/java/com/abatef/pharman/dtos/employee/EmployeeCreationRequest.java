package com.abatef.pharman.dtos.employee;

import com.abatef.pharman.enums.Sex;
import com.abatef.pharman.utils.Constants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreationRequest {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    private String username;
    @NotNull
    @NotEmpty
    @Size(min = 8, max = 20)
    private String password;
    @NotNull
    @Min(18)
    private Short age;
    private Sex sex;
    @NotNull
    @NotEmpty
    @Min(0)
    @Max(Constants.Numbers.FLOAT_MAX_VALUE)
    private Float salary;
    @NotNull
    @NotEmpty
    private String workStatus;
}
