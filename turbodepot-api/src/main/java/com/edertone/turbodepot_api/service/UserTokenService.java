package com.edertone.turbodepot_api.service;

import com.edertone.turbodepot_api.model.dto.AuthResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * Authentication user token business methods.
 */
public interface UserTokenService {

    /**
     * Create a new authentication token from one {@link Authentication} object, and map it to {@link AuthResponseDto}.
     * Note that the authentication object principal must be an object of type
     * {@link com.edertone.turbodepot_api.config.security.ApiUserDetails}.
     *
     * @param authentication the authentication
     * @return the created auth token as {@link AuthResponseDto}
     */
    AuthResponseDto createToAuthResponse(Authentication authentication);

    /**
     * Extract an {@link Authentication} from a {@link HttpServletRequest}, if present.
     *
     * @param request the request
     * @return the authentication or null
     */
    Authentication getAuthentication(HttpServletRequest request);

    /**
     * Scheduled task to delete expired tokens.
     */
    void deleteExpiredTokens();

    /**
     * Delete one user token. This method does not fail if the token does not exist.
     *
     * @param token the user token
     */
    void deleteToken(String token);
}
