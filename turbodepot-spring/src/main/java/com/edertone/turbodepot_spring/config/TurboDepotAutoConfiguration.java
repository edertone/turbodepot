package com.edertone.turbodepot_spring.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
import java.util.Map;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for TurboDepot.
 */
@AutoConfiguration
@ComponentScan("com.edertone.turbodepot_spring")
public class TurboDepotAutoConfiguration {

    @Value("${turbodepot-spring.model.table-prefix:usr_}")
    private String modelTablePrefix;

    @Bean
    public Flyway flyway(DataSource dataSource) {
        var flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration/turbodepot-spring")
            .table("_schema_version_turbodepot_spring")
            .placeholders(Map.of("table-prefix", modelTablePrefix))
            .baselineOnMigrate(true)
            .load();
        flyway.migrate();

        return flyway;
    }
}
