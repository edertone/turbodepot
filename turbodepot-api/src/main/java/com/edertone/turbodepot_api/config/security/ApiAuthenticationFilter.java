package com.edertone.turbodepot_api.config.security;

import com.edertone.turbodepot_api.service.UserTokenService;
import com.edertone.turbodepot_api.support.web.WebConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

import static com.edertone.turbodepot_api.support.web.WebUtils.requireParameter;

/**
 * Filter that handles authentication requests to the login URL.
 * <p>
 * Expects a POST request with <code>application/x-www-form-urlencoded</code> content type containing the following
 * parameters:
 * <ul>
 *     <li><strong>username</strong>: the username</li>
 *     <li><strong>password</strong>: the password</li>
 *     <li><strong>tenant</strong>: the tenant identifier</li>
 * </ul>
 * <p>
 * On successful authentication, returns a JSON response with the authentication token.
 * <p>
 * On failure, delegates to the provided {@link AuthenticationFailureHandler}.
 */
public class ApiAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserTokenService userTokenService;
    private final JsonMapper jsonMapper;

    public ApiAuthenticationFilter(
        AuthenticationManager authenticationManager,
        UserTokenService userTokenService,
        AuthenticationFailureHandler authenticationFailureHandler,
        JsonMapper jsonMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.userTokenService = userTokenService;
        this.jsonMapper = jsonMapper;

        setFilterProcessesUrl(WebConstants.AUTH_LOGIN_URL);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        if (!MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType())) {
            throw new AuthenticationServiceException("Content type not supported: " + request.getContentType());
        }

        var username = requireParameter(request, WebConstants.PARAM_USERNAME);
        var tenant = requireParameter(request, WebConstants.PARAM_TENANT);
        var password = requireParameter(request, WebConstants.PARAM_PASSWORD);

        var preAuthentication = UsernameTenantPasswordAuthenticationToken.unauthenticated(username, password, tenant);
        return authenticationManager.authenticate(preAuthentication);
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult
    ) throws IOException {
        var authResponse = userTokenService.createToAuthResponse(authResult);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonMapper.writeValueAsString(authResponse));
        response.getWriter().flush();
    }
}
