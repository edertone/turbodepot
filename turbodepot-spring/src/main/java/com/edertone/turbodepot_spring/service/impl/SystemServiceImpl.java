package com.edertone.turbodepot_spring.service.impl;

import com.edertone.turbodepot_spring.model.dto.SystemVersionDto;
import com.edertone.turbodepot_spring.service.SystemService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component("TurboDepotSystemService")
public class SystemServiceImpl implements SystemService {

    private final SystemVersionDto systemVersion;

    public SystemServiceImpl() {
        this.systemVersion = getSystemVersionDto();
    }

    @Override
    public SystemVersionDto getSystemVersion() {
        return systemVersion;
    }

    /**
     * Extract the system version from the package version, and from the git.properties file. Values fallback to
     * 'unknown' if there are no such properties.
     *
     * @return the system version information
     */
    private SystemVersionDto getSystemVersionDto() {
        var version = getClass().getPackage().getImplementationVersion();
        var properties = new Properties();

        try {
            var resource = new ClassPathResource("git.properties");
            if (resource.exists()) {
                properties.load(resource.getInputStream());
            }
        } catch (Exception e) {
            // ignore - we'll keep default values
        }

        var gitCommit = properties.getProperty("git.commit.id.abbrev", "unknown");
        var gitBranch = properties.getProperty("git.branch", "unknown");
        var buildTime = properties.getProperty("git.build.time", "unknown");

        return new SystemVersionDto(
            version == null ? "unknown" : version,
            gitCommit,
            gitBranch,
            buildTime
        );
    }
}
