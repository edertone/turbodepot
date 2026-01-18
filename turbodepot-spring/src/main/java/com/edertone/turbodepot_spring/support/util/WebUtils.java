package com.edertone.turbodepot_spring.support.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationServiceException;

import java.util.Optional;

/**
 * Web utility methods.
 */
public class WebUtils {

    // Authentication and security
    public static final String AUTH_LOGIN_URL = "/api/1.0/auth/login";
    public static final String AUTH_LOGOUT_URL = "/api/1.0/auth/logout";
    public static final String AUTH_CHECK_URL = "/api/1.0/auth/check";
    public static final String[] SWAGGER_URLS = {
        "/swagger-ui/**",
        "/api-docs/**"
    };
    public static final String[] PUBLIC_URLS = {
        "/api/public/**"
    };

    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_TENANT = "tenant";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_TOKEN = "token";
    public static final String TOKEN_PREFIX = "Bearer ";

    private WebUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Get a parameter from the request or throw an exception if it's missing or blank.
     *
     * @param request   the request
     * @param parameter the parameter name
     */
    public static String requireParameter(HttpServletRequest request, String parameter) {
        return Optional
                .ofNullable(request.getParameter(parameter))
                .filter(p -> !p.isBlank())
                .orElseThrow(() -> new AuthenticationServiceException("Missing parameter: " + parameter));
    }
}
