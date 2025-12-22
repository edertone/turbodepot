package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.*;

/**
 * User role entity.
 */
@Entity(name = "UserobjectRole")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dbid")
    private User user;

    @Column(name = "arrayindex", nullable = false)
    private long arrayIndex;

    public UserRoleId getId() {
        return id;
    }

    public UserRole setId(UserRoleId id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserRole setUser(User user) {
        this.user = user;
        return this;
    }

    public long getArrayIndex() {
        return arrayIndex;
    }

    public UserRole setArrayIndex(long arrayIndex) {
        this.arrayIndex = arrayIndex;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof UserRole other)) {
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
