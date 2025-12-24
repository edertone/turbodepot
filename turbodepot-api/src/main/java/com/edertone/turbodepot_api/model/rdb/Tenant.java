package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Tenant entity.
 */
@Entity
public class Tenant {

    @Id
    @Column(nullable = false, length = 250)
    private String name;

    @Column(nullable = false, length = 5000)
    private String description;

    public String getName() {
        return name;
    }

    public Tenant setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Tenant setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Tenant other)) {
            return false;
        } else {
            return this.getName() != null && this.getName().equals(other.getName());
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
