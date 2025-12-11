package com.edertone.turbodepot_spring.service;

import com.edertone.turbodepot_spring.model.dto.AuthResponseDto;
import org.springframework.security.core.Authentication;

/**
 * Authentication user token business methods.
 */
public interface UserTokenService {

    /**
     * Create a new authentication token from one {@link Authentication} object, and map it to {@link AuthResponseDto}.
     * Note that the authentication object principal must be an object of type
     * {@link com.edertone.turbodepot_spring.config.security.ApiUserDetails}.
     *
     * @param authentication the authentication
     * @return the created auth token as {@link AuthResponseDto}
     */
    AuthResponseDto createToAuthResponse(Authentication authentication);

    /**
     * Scheduled task to delete expired tokens.
     */
    void deleteExpiredTokens();
}
