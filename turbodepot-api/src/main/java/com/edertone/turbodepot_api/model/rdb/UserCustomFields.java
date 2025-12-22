package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.*;

/**
 * User custom fields entity.
 */
@Entity(name = "UserobjectCustomfields")
public class UserCustomFields {

    @Id
    @Column(name = "dbid")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dbid")
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 200)
    private String surnames;

    @Column(length = 25)
    private String phone;

    @Column(length = 200)
    private String company;

    @Column(length = 200)
    private String occupation;

    @Column(length = 300)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String district;

    @Column(name = "postalcode", length = 20)
    private String postalCode;

    public Long getId() {
        return id;
    }

    public UserCustomFields setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserCustomFields setUser(User user) {
        this.user = user;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserCustomFields setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurnames() {
        return surnames;
    }

    public UserCustomFields setSurnames(String surnames) {
        this.surnames = surnames;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserCustomFields setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public UserCustomFields setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getOccupation() {
        return occupation;
    }

    public UserCustomFields setOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserCustomFields setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserCustomFields setCity(String city) {
        this.city = city;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public UserCustomFields setDistrict(String district) {
        this.district = district;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public UserCustomFields setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof UserCustomFields other)) {
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
