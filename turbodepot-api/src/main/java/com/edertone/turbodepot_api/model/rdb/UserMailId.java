package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;

/**
 * User mail composite primary key.
 */
@Embeddable
public class UserMailId implements Serializable {

    @Serial
    private static final long serialVersionUID = -1347577139800597106L;

    @Column(name = "dbid")
    private Long id;

    @Column(nullable = false, length = 250)
    private String mail;

    public Long getId() {
        return id;
    }

    public UserMailId setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public UserMailId setMail(String mail) {
        this.mail = mail;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof UserMailId other)) {
            return false;
        } else {
            return this.getId() != null && this.getId().equals(other.getId()) &&
                   this.getMail() != null && this.getMail().equals(other.getMail());
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
