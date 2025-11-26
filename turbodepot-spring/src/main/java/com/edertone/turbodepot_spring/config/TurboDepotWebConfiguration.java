package com.edertone.turbodepot_spring.config;

import com.edertone.turbodepot_spring.controller.VersionController;
import com.edertone.turbodepot_spring.service.SystemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TurboDepotWebConfiguration {

    @Bean
    public VersionController versionController(SystemService systemService) {
        return new VersionController(systemService);
    }
}
