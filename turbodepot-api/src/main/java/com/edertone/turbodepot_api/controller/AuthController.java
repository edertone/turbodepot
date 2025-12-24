package com.edertone.turbodepot_api.controller;

import com.edertone.turbodepot_api.model.dto.AuthResponseDto;
import com.edertone.turbodepot_api.support.web.WebConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>NOTE</b>: this controller is just for Swagger documentation purposes. The endpoints described here are managed
 * at filter level.
 */
@RestController
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    @Operation(
        summary = "User login",
        description = "Authenticates a user and returns an authentication token",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful authentication",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AuthResponseDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Authentication failed"
            )
        }
    )
    @PostMapping(value = WebConstants.AUTH_LOGIN_URL,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void login(
        @Parameter(description = "Username", required = true, example = "user@example.com")
        @RequestParam String username,
        @Parameter(description = "Password", required = true)
        @RequestParam String password,
        @Parameter(description = "Tenant identifier", required = true, example = "tenant1")
        @RequestParam String tenant
    ) {
        throw new IllegalStateException("This method should never be called");
    }

    @Operation(
        summary = "User logout",
        description = "Logout a user",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful logout"
            )
        }
    )
    @PostMapping(value = WebConstants.AUTH_LOGOUT_URL)
    public void logout(
    ) {
        throw new IllegalStateException("This method should never be called");
    }

    @Operation(
        summary = "Check token",
        description = "Check authentication token",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful logout"
            )
        }
    )
    @PostMapping(value = WebConstants.AUTH_CHECK_URL)
    public void checkToken(
    ) {
        throw new IllegalStateException("This method should never be called");
    }
}
