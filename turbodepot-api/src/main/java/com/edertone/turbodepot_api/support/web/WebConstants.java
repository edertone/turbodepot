package com.edertone.turbodepot_api.support.web;

/**
 * Web constants.
 */
public class WebConstants {

    private WebConstants() {
        throw new IllegalStateException("Utility class");
    }

    // Authentication and security
    public static final String AUTH_LOGIN_URL = "/api/1.0/auth/login";
    public static final String AUTH_LOGOUT_URL = "/api/1.0/auth/logout";
    public static final String AUTH_CHECK_URL = "/api/1.0/auth/check";
    public static final String[] SWAGGER_URLS = {
        "/swagger-ui/**",
        "/api-docs/**"
    };

    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_TENANT = "tenant";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_TOKEN = "token";
    public static final String TOKEN_PREFIX = "Bearer ";
}
