package com.edertone.turbodepot_api.model.rdb;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Date;

/**
 * This class is used to store some common entity fields.
 *
 * @param <S> the type of the entity that extends this class
 */
@MappedSuperclass
public abstract class CommonEntity<S> {

    @Column(insertable = false, updatable = false, nullable = false, unique = true, columnDefinition = "varchar(36) not null default uuid()")
    private String dbuuid;

    @Column(name = "dbcreationdate", nullable = false)
    private Date creationDate;

    @Column(name = "dbmodificationdate", nullable = false)
    private Date modificationDate;

    @Column(name = "dbdeleted")
    private Date deletionDate;

    @PrePersist
    public void prePersist() {
        var now = new Date();
        setCreationDate(now);
        setModificationDate(now);
    }

    @PreUpdate
    public void preUpdate() {
        setModificationDate(new Date());
    }

    public String getDbuuid() {
        return dbuuid;
    }

    @SuppressWarnings("unchecked")
    public S setDbuuid(String dbuuid) {
        this.dbuuid = dbuuid;
        return (S) this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @SuppressWarnings("unchecked")
    public S setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return (S) this;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    @SuppressWarnings("unchecked")
    public S setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
        return (S) this;
    }

    public Date getDeletionDate() {
        return deletionDate;
    }

    @SuppressWarnings("unchecked")
    public S setDeletionDate(Date deletionDate) {
        this.deletionDate = deletionDate;
        return (S) this;
    }
}
