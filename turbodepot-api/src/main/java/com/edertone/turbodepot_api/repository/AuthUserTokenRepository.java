package com.edertone.turbodepot_api.repository;

import com.edertone.turbodepot_api.model.rdb.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link UserToken} entity repository.
 */
@Repository
public interface AuthUserTokenRepository extends JpaRepository<UserToken, Long>, JpaSpecificationExecutor<UserToken> {

    /**
     * Delete token with expiration date is in the past.
     *
     * @return the number of deleted tokens
     */
    @Modifying
    @Query("delete from Token ut where ut.expirationDate < current_timestamp")
    int deleteExpiredTokens();

    /**
     * Find one user token by one token value.
     *
     * @param token the token value
     * @return the user token
     */
    Optional<UserToken> findByToken(String token);
}
