package com.edertone.turbodepot_spring.service;

import com.edertone.turbodepot_spring.model.dto.SystemVersionDto;

/**
 * Current system/application services.
 */
public interface SystemService {

    /**
     * Get the system version information.
     *
     * @return the system version
     */
    SystemVersionDto getSystemVersion();
}
