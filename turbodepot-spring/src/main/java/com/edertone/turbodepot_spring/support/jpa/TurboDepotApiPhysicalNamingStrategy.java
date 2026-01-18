package com.edertone.turbodepot_spring.support.jpa;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategySnakeCaseImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@link org.hibernate.boot.model.naming.PhysicalNamingStrategy} that uses the value of the
 * <code>turbodepot-spring.model.table-prefix</code> property as a table name prefix. This strategy is only
 * applied to this library model logical names.
 */
@Component
public class TurboDepotApiPhysicalNamingStrategy extends PhysicalNamingStrategySnakeCaseImpl implements ApplicationContextAware {

    private final List<String> managedEntities = List.of(
        "TurboDepotOperation",
        "TurboDepotOperationRole",
        "TurboDepotRole",
        "TurboDepotTenant",
        "TurboDepotUser",
        "TurboDepotUserCustomFields",
        "TurboDepotUserMail",
        "TurboDepotUserPassword",
        "TurboDepotUserRole",
        "TurboDepotUserToken"
    );

    private String tablePrefix;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        tablePrefix = applicationContext.getEnvironment().getProperty("turbodepot-spring.model.table-prefix", "usr_");
    }

    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        if (managedEntities.contains(logicalName.getText())) {
            return super.toPhysicalTableName(
                Identifier.toIdentifier(tablePrefix + logicalName.getText().replace("TurboDepot", "")),
                jdbcEnvironment);
        }

        return super.toPhysicalTableName(logicalName, jdbcEnvironment);
    }
}
