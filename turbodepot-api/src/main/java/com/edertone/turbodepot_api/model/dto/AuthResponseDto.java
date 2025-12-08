package com.edertone.turbodepot_api.model.dto;

import java.util.Collection;

/**
 * Authentication response DTO.
 *
 * @param token      the authentication token
 * @param user       the authenticated user
 * @param operations list of allowed operations (granted authorities)
 */
public record AuthResponseDto(
    String token,
    AuthUserResponseDto user,
    Collection<String> operations
) {
}
