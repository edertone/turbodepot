package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Operation role composite primary key.
 */
@Embeddable
public class OperationRoleId implements Serializable {

    @Serial
    private static final long serialVersionUID = -3003362499241297550L;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant", referencedColumnName = "name", insertable = false, updatable = false)
    private Tenant tenant;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "tenant", referencedColumnName = "tenant"),
            @JoinColumn(name = "operation", referencedColumnName = "name")
    })
    private Operation operation;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "tenant", referencedColumnName = "tenant"),
            @JoinColumn(name = "role", referencedColumnName = "name")
    })
    private Role role;

    public Tenant getTenant() {
        return tenant;
    }

    public OperationRoleId setTenant(Tenant tenant) {
        this.tenant = tenant;
        return this;
    }

    public Operation getOperation() {
        return operation;
    }

    public OperationRoleId setOperation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public OperationRoleId setRole(Role role) {
        this.role = role;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof OperationRoleId other)) {
            return false;
        } else {
            return this.getTenant() != null && this.getTenant().equals(other.getTenant()) &&
                   this.getOperation() != null && this.getOperation().equals(other.getOperation()) &&
                   this.getRole() != null && this.getRole().equals(other.getRole());
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
