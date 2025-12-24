package com.edertone.turbodepot_spring.model.dto;

import java.time.ZonedDateTime;

/**
 * Error response dto.
 */
public record ErrorResponseDto(String errorCode, Object message) {

    public ZonedDateTime getDate() {
        return ZonedDateTime.now();
    }
}
