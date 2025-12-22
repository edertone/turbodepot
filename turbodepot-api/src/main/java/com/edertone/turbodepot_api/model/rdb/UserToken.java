package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.*;

import java.util.Date;

/**
 * UserToken entity.
 */
@Entity(name = "Token")
public class UserToken {

    @Id
    @Column(name = "dbid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usrdbid", updatable = false)
    private User user;

    @Column(nullable = false, unique = true, columnDefinition = "text")
    private String token;

    @Column(name = "dbcreationdate", nullable = false)
    private Date creationDate;

    @Column(name = "dbexpirationdate", nullable = false)
    private Date expirationDate;

    @PrePersist
    public void prePersist() {
        setCreationDate(new Date());
    }

    public Integer getId() {
        return id;
    }

    public UserToken setId(Integer id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserToken setUser(User user) {
        this.user = user;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserToken setToken(String token) {
        this.token = token;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public UserToken setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public UserToken setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof UserToken other)) {
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
