package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.*;

/**
 * User password entity.
 */
@Entity(name = "UserobjectPassword")
public class UserPassword {

    @Id
    @Column(name = "dbid")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dbid")
    private User user;

    @Column(nullable = false, length = 500)
    private String password;

    public Long getId() {
        return id;
    }

    public UserPassword setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserPassword setUser(User user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserPassword setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof UserPassword other)) {
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
