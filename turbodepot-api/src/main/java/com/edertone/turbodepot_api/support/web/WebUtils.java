package com.edertone.turbodepot_api.support.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationServiceException;

import java.util.Optional;

/**
 * Web utility methods.
 */
public class WebUtils {

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
