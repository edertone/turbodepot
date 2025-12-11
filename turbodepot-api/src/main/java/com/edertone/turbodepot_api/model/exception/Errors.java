package com.edertone.turbodepot_api.model.exception;

/**
 * List of error codes.
 */
public class Errors {

    private Errors() {
        throw new IllegalStateException("Utility class");
    }

    // Security
    public static final String UNAUTHORIZED_MESSAGE = "unauthorized";
}
