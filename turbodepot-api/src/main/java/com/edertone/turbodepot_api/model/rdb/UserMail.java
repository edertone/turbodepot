package com.edertone.turbodepot_api.model.rdb;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.Map;

/**
 * User mail entity.
 */
@Entity(name = "UserobjectMail")
public class UserMail {

    @EmbeddedId
    private UserMailId id;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dbid")
    private User user;

    @Column(name = "isverified", nullable = false, columnDefinition = "default false")
    private boolean verified;

    @Column(name = "verificationhash", length = 20)
    private String verificationHash;

    @Column(length = 1000)
    private String comments;

    @Type(JsonType.class)
    @Column(columnDefinition = "longtext")
    private Map<String, Object> data;

    public UserMailId getId() {
        return id;
    }

    public UserMail setId(UserMailId id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserMail setUser(User user) {
        this.user = user;
        return this;
    }

    public boolean isVerified() {
        return verified;
    }

    public UserMail setVerified(boolean verified) {
        this.verified = verified;
        return this;
    }

    public String getVerificationHash() {
        return verificationHash;
    }

    public UserMail setVerificationHash(String verificationHash) {
        this.verificationHash = verificationHash;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public UserMail setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public UserMail setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof UserMail other)) {
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
