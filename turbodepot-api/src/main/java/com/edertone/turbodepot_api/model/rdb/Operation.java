package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.*;

/**
 * Operation entity.
 */
@Entity
public class Operation {

    @EmbeddedId
    private OperationId id;

    @Column(nullable = false, length = 250)
    private String description;

    @MapsId("tenant")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant", referencedColumnName = "name")
    private Tenant tenant;

    public OperationId getId() {
        return id;
    }

    public Operation setId(OperationId id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Operation setDescription(String description) {
        this.description = description;
        return this;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public Operation setTenant(Tenant tenant) {
        this.tenant = tenant;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Operation other)) {
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
