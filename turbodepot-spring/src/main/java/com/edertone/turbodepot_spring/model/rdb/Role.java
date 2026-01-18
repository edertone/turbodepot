package com.edertone.turbodepot_spring.model.rdb;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

/**
 * Role entity.
 */
@Entity(name = "TurboDepotRole")
public class Role {

    @EmbeddedId
    private RoleId id;

    @Column(nullable = false, length = 5000)
    private String description;

    public RoleId getId() {
        return id;
    }

    public Role setId(RoleId id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Role setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Role other)) {
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
