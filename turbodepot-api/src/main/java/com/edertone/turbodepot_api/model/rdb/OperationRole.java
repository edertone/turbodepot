package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Operation role entity.
 */
@Entity
public class OperationRole {

    @EmbeddedId
    private OperationRoleId id;

    public OperationRoleId getId() {
        return id;
    }

    public OperationRole setId(OperationRoleId id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof OperationRole other)) {
            return false;
        } else {
            return this.getId() != null && this.getId().equals(other.getId());
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
