package com.edertone.turbodepot_api.config.security;

import com.edertone.turbodepot_api.service.UserTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter to set up Spring authorization from the current request, if present.
 * Refer to {@link UserTokenService} for more information.
 */
public class ApiAuthorizationFilter extends OncePerRequestFilter {

    private final UserTokenService userTokenService;

    public ApiAuthorizationFilter(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        var authentication = userTokenService.getAuthentication(request);

        if (authentication != null) {
            logger.trace(String.format("Authentication found: %s", authentication));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            logger.trace("No authentication found");
        }

        filterChain.doFilter(request, response);
    }
}
