package com.abatef.pharman.models.employee;

import com.abatef.pharman.enums.EmployeeRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EmpRoleId implements java.io.Serializable {
    private static final long serialVersionUID = 4340818617696866009L;
    @NotNull
    @Column(name = "emp_id", nullable = false)
    private Integer empId;

    @Size(max = 10)
    @NotNull
    @Column(name = "role", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private EmployeeRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EmpRoleId entity = (EmpRoleId) o;
        return Objects.equals(this.empId, entity.empId) &&
                Objects.equals(this.role, entity.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, role);
    }

}