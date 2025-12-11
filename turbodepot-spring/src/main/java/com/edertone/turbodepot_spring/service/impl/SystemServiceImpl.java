package com.edertone.turbodepot_spring.service.impl;

import com.edertone.turbodepot_spring.model.dto.SystemVersionDto;
import com.edertone.turbodepot_spring.service.SystemService;

public class SystemServiceImpl implements SystemService {

    private final SystemVersionDto systemVersion;

    public SystemServiceImpl(SystemVersionDto systemVersion) {
        this.systemVersion = systemVersion;
    }

    @Override
    public SystemVersionDto getSystemVersion() {
        return systemVersion;
    }
}
