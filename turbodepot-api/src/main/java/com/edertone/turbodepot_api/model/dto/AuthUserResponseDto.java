package com.edertone.turbodepot_api.model.dto;

import java.util.List;
import java.util.Map;

/**
 * Authentication user response DTO.
 *
 * @param tenant      the tenant
 * @param roles       list of roles
 * @param username    the username
 * @param description the description
 * @param mails       list of mails
 * @param data        additional data
 */
public record AuthUserResponseDto(
    String tenant,
    List<String> roles,
    String username,
    String description,
    List<String> mails,
    Map<String, Object> data
) {
}
