package com.edertone.turbodepot_spring.model.dto;

import java.time.ZonedDateTime;

/**
 * Error response dto.
 *
 * @param errorCode the error code
 * @param message   the message
 */
public record ErrorResponseDto(String errorCode, Object message) {

    public ZonedDateTime getDate() {
        return ZonedDateTime.now();
    }
}
