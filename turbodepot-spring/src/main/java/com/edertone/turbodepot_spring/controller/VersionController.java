package com.edertone.turbodepot_spring.controller;

import com.edertone.turbodepot_spring.model.dto.SystemVersionDto;
import com.edertone.turbodepot_spring.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("TurboDepotVersionController")
@RequestMapping("/api/public/version")
public class VersionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SystemService systemService;

    public VersionController(SystemService systemService) {
        this.systemService = systemService;
    }

    @GetMapping
    public SystemVersionDto getVersion() {
        logger.debug("getVersion");
        return systemService.getSystemVersion();
    }
}
