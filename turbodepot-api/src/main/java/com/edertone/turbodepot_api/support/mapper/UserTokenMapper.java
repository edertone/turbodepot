package com.edertone.turbodepot_api.support.mapper;

import com.edertone.turbodepot_api.model.dto.AuthResponseDto;
import com.edertone.turbodepot_api.model.dto.AuthUserResponseDto;
import com.edertone.turbodepot_api.model.rdb.*;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Mapper for {@link UserToken}.
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserTokenMapper {

    /**
     * Map an authentication and an auth user token to an auth response dto.
     *
     * @param authentication the authentication
     * @param userToken  the auth user token
     * @return the auth response dto
     */
    default AuthResponseDto toAuthResponseDto(Authentication authentication, UserToken userToken) {
        var user = userToken.getUser();
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
            userToken.getToken(),
            userResponse,
            authentication.getAuthorities().stream().filter(SimpleGrantedAuthority.class::isInstance).map(Object::toString).toList());
    }
}
