package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;

/**
 * User role composite primary key.
 */
@Embeddable
public class UserRoleId implements Serializable {

    @Serial
    private static final long serialVersionUID = 6279065569997589872L;

    @Column(name = "dbid")
    private Long id;

    @Column(nullable = false, length = 250)
    private String value;

    public Long getId() {
        return id;
    }

    public UserRoleId setId(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    public UserRoleId setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof UserRoleId other)) {
            return false;
        } else {
            return this.getId() != null && this.getId().equals(other.getId()) &&
                   this.getValue() != null && this.getValue().equals(other.getValue());
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
