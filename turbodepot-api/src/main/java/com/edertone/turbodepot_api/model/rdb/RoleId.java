package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Role composite primary key.
 */
@Embeddable
public class RoleId {

    @Column(nullable = false, length = 250)
    private String tenant;

    @Column(nullable = false, length = 250)
    private String name;

    public String getTenant() {
        return tenant;
    }

    public RoleId setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoleId setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof RoleId other)) {
            return false;
        } else {
            return this.getTenant() != null && this.getTenant().equals(other.getTenant()) &&
                   this.getName() != null && this.getName().equals(other.getName());
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
