package com.edertone.turbodepot_api.model.rdb;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User entity.
 */
@Entity(name = "Userobject")
public class User extends CommonEntity<User> {

    @Id
    @Column(name = "dbid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant", referencedColumnName = "name", nullable = false, updatable = false)
    private Tenant tenant;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(length = 2000)
    private String description;

    @Type(JsonType.class)
    @Column(columnDefinition = "longtext")
    private Map<String, Object> data;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserPassword password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserCustomFields customFields;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserMail> mails = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public User setTenant(Tenant tenant) {
        this.tenant = tenant;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public User setDescription(String description) {
        this.description = description;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public User setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public UserPassword getPassword() {
        return password;
    }

    public User setPassword(UserPassword password) {
        this.password = password;
        return this;
    }

    public UserCustomFields getCustomFields() {
        return customFields;
    }

    public User setCustomFields(UserCustomFields customFields) {
        this.customFields = customFields;
        return this;
    }

    public Set<UserMail> getMails() {
        return mails;
    }

    public User setMails(Set<UserMail> mails) {
        this.mails = mails;
        return this;
    }

    public User addMail(UserMail mail) {
        mails.add(mail);
        mail.setUser(this);
        return this;
    }

    public User removeMail(UserMail mail) {
        mails.remove(mail);
        mail.setUser(null);
        return this;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public User setRoles(Set<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public User addMail(UserRole role) {
        roles.add(role);
        role.setUser(this);
        return this;
    }

    public User removeMail(UserRole role) {
        roles.remove(role);
        role.setUser(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof User other)) {
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
