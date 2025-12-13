package com.edertone.turbodepot_api.support.mapper;

import com.edertone.turbodepot_api.config.security.ApiUserDetails;
import com.edertone.turbodepot_api.model.dto.AuthResponseDto;
import com.edertone.turbodepot_api.model.dto.AuthUserResponseDto;
import com.edertone.turbodepot_api.model.rdb.*;
import com.edertone.turbodepot_api.support.web.WebConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Optional;

/**
 * Mapper for {@link UserToken}.
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserTokenMapper {

    /**
     * Map an authentication and an auth user token to an auth response dto.
     *
     * @param authentication the authentication
     * @param token          the auth user token
     * @return the auth response dto
     */
    default AuthResponseDto toAuthResponseDto(Authentication authentication, String token) {
        var apiUserDetails = (ApiUserDetails) authentication.getPrincipal();
        var user = apiUserDetails.getUser();
        var roles = user.getRoles().stream().map(UserRole::getId).map(UserRoleId::getValue).sorted().toList();
        var emails = user.getMails().stream().map(UserMail::getId).map(UserMailId::getMail).sorted().toList();

        var userResponse = new AuthUserResponseDto(
            user.getTenant().getName(),
            roles,
            user.getUsername(),
            user.getDescription(),
            emails,
            user.getData()
        );

        return new AuthResponseDto(
            token,
            userResponse,
            authentication.getAuthorities().stream().filter(SimpleGrantedAuthority.class::isInstance).map(Object::toString).toList());
    }

    /**
     * Extracts the token from a request. The token must be in the Authorization header and optionally start with
     * {@link WebConstants#TOKEN_PREFIX}.
     * <p>
     * If there is no such header, null is returned.
     *
     * @param source the request
     * @return the token or null
     */
    default String toToken(HttpServletRequest source) {
        return Optional
            .ofNullable(source.getHeader(HttpHeaders.AUTHORIZATION))
            .map(value -> value.replace(WebConstants.TOKEN_PREFIX, ""))
            .orElse(null);
    }
}
