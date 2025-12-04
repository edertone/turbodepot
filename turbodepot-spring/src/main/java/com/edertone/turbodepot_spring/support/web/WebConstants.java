package com.edertone.turbodepot_spring.support.web;

/**
 * Web constants.
 */
public class WebConstants {

    private WebConstants() {
        throw new IllegalStateException("Utility class");
    }

    // Authentication and security
    public static final String DEFAULT_AUTH_LOGIN_URL = "/api/1.0/auth/login";
    public static final String[] DEFAULT_SWAGGER_URLS = {
        "/swagger-ui/**",
        "/api-docs/**"
    };
    public static final String[] DEFAULT_PUBLIC_URLS = {
        "/public/**"
    };

    public static final String DEFAULT_PARAM_USERNAME = "username";
    public static final String DEFAULT_PARAM_TENANT = "tenant";
    public static final String DEFAULT_PARAM_PASSWORD = "password";
}
