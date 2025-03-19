package com.abatef.pharman.models.employee;

import com.abatef.pharman.enums.EmployeeRole;
import com.abatef.pharman.enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('employee_emp_id_seq')")
    @Column(name = "emp_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
    private String password;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "age")
    private Short age;

    @Column(name = "sex", length = 2)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "work_status", length = 20)
    private String workStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "emp")
    private Set<EmpRole> empRoles = new LinkedHashSet<>();

    public EmpRole createRole(EmployeeRole role) {
        EmpRoleId id = new EmpRoleId(this.id, role);
        EmpRole empRole = new EmpRole();
        empRole.setId(id);
        empRole.setEmp(this);
        return empRole;
    }

    public void removeEmpRole(EmployeeRole role) {
        EmpRoleId id = new EmpRoleId(this.id, role);
        empRoles.removeIf(empRole -> empRole.getId().getRole() == role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return empRoles.stream().map(empRole -> {
            return new SimpleGrantedAuthority("ROLE_" + empRole.getId().getRole().name());
        }).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}