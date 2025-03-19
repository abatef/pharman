package com.abatef.pharman.repositories;

import com.abatef.pharman.models.employee.EmpRole;
import com.abatef.pharman.models.employee.EmpRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpRoleRepository extends JpaRepository<EmpRole, EmpRoleId> {
}
