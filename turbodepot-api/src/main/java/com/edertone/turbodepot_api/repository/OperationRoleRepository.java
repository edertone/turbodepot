package com.edertone.turbodepot_api.repository;

import com.edertone.turbodepot_api.model.rdb.OperationRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * {@link OperationRole} entity repository.
 */
@Repository
public interface OperationRoleRepository extends JpaRepository<OperationRole, Long>, JpaSpecificationExecutor<OperationRole> {

    @Query("""
        select opr from OperationRole opr
        where
            opr.id.role.id.name in :roles and
            opr.id.tenant.name = :tenant
    """)
    Set<OperationRole> findByRolesAndTenant(List<String> roles, String tenant);
}
