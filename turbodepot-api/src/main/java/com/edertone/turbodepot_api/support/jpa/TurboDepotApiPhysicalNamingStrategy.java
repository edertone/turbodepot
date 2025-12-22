package com.edertone.turbodepot_api.support.jpa;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategySnakeCaseImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * {@link org.hibernate.boot.model.naming.PhysicalNamingStrategy} that uses the value of the
 * <code>turbodepot-api.model.table-prefix</code> property as a table name prefix.
 */
@Component
public class TurboDepotApiPhysicalNamingStrategy extends PhysicalNamingStrategySnakeCaseImpl implements ApplicationContextAware {

    private String tablePrefix;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        tablePrefix = applicationContext.getEnvironment().getProperty("turbodepot-api.model.table-prefix", "usr_");
    }

    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        return super.toPhysicalTableName(
            Identifier.toIdentifier(tablePrefix + logicalName.getText()),
            jdbcEnvironment);
    }
}
