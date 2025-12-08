package com.edertone.turbodepot_api.config.security;

import com.edertone.turbodepot_api.model.dto.ErrorResponseDto;
import com.edertone.turbodepot_api.model.exception.Errors;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

/**
 * Helper class to handle authentication failures, implementing both {@link AuthenticationFailureHandler} and
 * {@link AuthenticationEntryPoint} because the same response is expected in both cases.
 * <p>
 * Authentication errors are returned as a JSON object from {@link ErrorResponseDto} with status code 401 (Unauthorized).
 */
@Component
public class ApiAuthenticationFailureHelper implements AuthenticationFailureHandler, AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(ApiAuthenticationFailureHelper.class);

    private final JsonMapper jsonMapper;

    public ApiAuthenticationFailureHelper(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException {
        writeResponse(request, response, exception);
    }

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException {
        writeResponse(request, response, exception);
    }

    /**
     * Write the failure response as a JSON object from {@link ErrorResponseDto}.
     * <p>
     * Refer to the {@link #getErrorCode(AuthenticationException)} method to get the error code to use.
     *
     * @param request       the servlet request
     * @param response      the servlet response
     * @param authException the authentication exception
     * @throws IOException  if an I/O error occurs
     */
    private void writeResponse(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException {
        logger.trace("Unauthorized error for URI [{}]. Cause: {}", request.getServletPath(), authException.getMessage());
        var errorCode = getErrorCode(authException);
        var bodyObject = new ErrorResponseDto(errorCode, authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(jsonMapper.writeValueAsString(bodyObject));
        response.getWriter().flush();
    }

    /**
     * Get the error code depending on the exception type.
     *
     * @param authException the authentication exception
     * @return the error code
     */
    private String getErrorCode(AuthenticationException authException) {
        return Errors.UNAUTHORIZED_MESSAGE;
    }
}
