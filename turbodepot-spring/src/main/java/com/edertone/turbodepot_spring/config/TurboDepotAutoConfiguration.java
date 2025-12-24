package com.edertone.turbodepot_spring.config;

import com.edertone.turbodepot_spring.model.dto.SystemVersionDto;
import com.edertone.turbodepot_spring.service.SystemService;
import com.edertone.turbodepot_spring.service.impl.SystemServiceImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for TurboDepot.
 */
@Configuration
@Import({ TurboDepotSecurityConfiguration.class, TurboDepotWebConfiguration.class })
public class TurboDepotAutoConfiguration {

    @Bean
    public SystemService systemService() {
        return new SystemServiceImpl(getSystemVersionDto());
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
