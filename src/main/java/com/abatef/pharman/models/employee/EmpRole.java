package com.abatef.pharman.models.employee;

import com.abatef.pharman.enums.EmployeeRole;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "emp_role")
public class EmpRole {
    @EmbeddedId
    private EmpRoleId id;

    @MapsId("empId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee emp;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static EmpRole of(Integer empId, EmployeeRole role) {
        EmpRoleId id = new EmpRoleId(empId, role);
        EmpRole empRole = new EmpRole();
        empRole.setId(id);
        return empRole;
    }

    public static EmpRole of(Employee emp, EmployeeRole role) {
        EmpRoleId id = new EmpRoleId(emp.getId(), role);
        EmpRole empRole = new EmpRole();
        empRole.setId(id);
        empRole.setEmp(emp);
        return empRole;
    }
}