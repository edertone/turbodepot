package com.edertone.turbodepot_api.config.security;

import com.edertone.turbodepot_api.service.UserTokenService;
import com.edertone.turbodepot_api.support.mapper.UserTokenMapper;
import com.edertone.turbodepot_api.support.web.WebConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.Collections;

import static org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher.pathPattern;

/**
 * Filter to check the authorization token, if present. Refer to {@link UserTokenService} for more information.
 */
public class ApiCheckTokenFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher = pathPattern(WebConstants.AUTH_CHECK_URL);
    private final UserTokenService userTokenService;
    private final UserTokenMapper userTokenMapper;
    private final JsonMapper jsonMapper;

    public ApiCheckTokenFilter(
        UserTokenService userTokenService,
        UserTokenMapper userTokenMapper,
        JsonMapper jsonMapper
    ) {
        this.userTokenService = userTokenService;
        this.userTokenMapper = userTokenMapper;
        this.jsonMapper = jsonMapper;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        if (!requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
        } else {
            var responseBody = getResponse(request);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(jsonMapper.writeValueAsString(responseBody));
            response.getWriter().flush();
        }
    }

    /**
     * Get the response to be returned. Check for the authentication and parse it to one
     * {@link com.edertone.turbodepot_api.model.dto.AuthResponseDto} object. If there is no such authentication, or
     * the token is expired, then return an empty map.
     *
     * @param request the request
     * @return token details or empty map
     */
    private Object getResponse(HttpServletRequest request) {
        var authentication = userTokenService.getAuthentication(request);
        var accessToken = userTokenMapper.toToken(request);

        if (authentication == null || accessToken == null) {
            return Collections.emptyMap();
        }

        return userTokenMapper.toAuthResponseDto(authentication, accessToken);
    }
}
