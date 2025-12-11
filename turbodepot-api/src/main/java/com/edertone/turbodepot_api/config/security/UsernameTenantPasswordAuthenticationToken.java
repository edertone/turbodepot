package com.edertone.turbodepot_api.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.Serial;
import java.util.Optional;

/**
 * Specialization of {@link UsernamePasswordAuthenticationToken} that includes a tenant identifier.
 */
public class UsernameTenantPasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final String SEPARATOR = "###";

    @Serial
    private static final long serialVersionUID = 2506030690831299080L;

    private final String tenant;

    public UsernameTenantPasswordAuthenticationToken(Object principal, Object credentials, String tenant) {
        super(principal, credentials);
        this.tenant = tenant;
    }

    public static UsernameTenantPasswordAuthenticationToken unauthenticated(
        Object principal, Object credentials, String tenant
    ) {
        return new UsernameTenantPasswordAuthenticationToken(principal, credentials, tenant);
    }

    @Override
    public String getName() {
        return tenant + SEPARATOR + super.getName();
    }

    /**
     * Utility method to retrieve the tenant from one name, throwing one exception if the name is not well formatted,
     * or the tenant part is missing.
     *
     * @param name the name
     * @return the tenant part of the name
     */
    public static String requireTenant(String name) {
        return Optional.ofNullable(name)
            .filter(n -> n.contains(SEPARATOR))
            .map(n -> n.split(SEPARATOR, 2)[0])
            .filter(t -> !t.isBlank())
            .orElseThrow(() -> new IllegalArgumentException("Tenant not found in name: " + name));
    }

    /**
     * Utility method to retrieve the username from one name, throwing one exception if the name is not well formatted,
     * or the username part is missing.
     *
     * @param name the name
     * @return the username part of the name
     */
    public static String requireUsername(String name) {
        return Optional.ofNullable(name)
            .filter(n -> n.contains(SEPARATOR))
            .map(n -> n.split(SEPARATOR, 2)[1])
            .filter(u -> !u.isBlank())
            .orElseThrow(() -> new IllegalArgumentException("Username not found in name: " + name));
    }
}
