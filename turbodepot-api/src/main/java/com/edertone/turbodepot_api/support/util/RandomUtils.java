package com.edertone.turbodepot_api.support.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Random utility methods.
 */
public class RandomUtils {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private RandomUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Generate one random token of 64 bytes in base64.
     *
     * @return random user token in base64
     */
    public static String generateUserToken() {
        return generateUrlSafeToken(64);
    }

    /**
     * Generate one random URL safe token of the given length, in base64.
     *
     * @param byteLength the random token length
     * @return random token in base64
     */
    private static String generateUrlSafeToken(int byteLength) {
        var bytes = new byte[byteLength];
        SECURE_RANDOM.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
