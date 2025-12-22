package com.edertone.turbodepot_api.repository;

import com.edertone.turbodepot_api.model.rdb.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link User} entity repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("""
        select u from Userobject u
            join fetch u.tenant t
            join fetch u.password p
            join fetch u.customFields cf
            join fetch u.mails m
            join fetch u.roles r
        where
            u.username = :username and
            t.name = :tenant
    """)
    Optional<User> findByUsernameAndTenant(String username, String tenant);
}
