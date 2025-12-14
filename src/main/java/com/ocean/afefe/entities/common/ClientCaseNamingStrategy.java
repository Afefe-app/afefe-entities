package com.ocean.afefe.entities.common;

import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class ClientCaseNamingStrategy implements PhysicalNamingStrategy {

    private static final String CUSTOM_TABLE_NAME_PREFIX = "";

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return name == null ? null : applyPrefix(name);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return name == null ? null : applyPrefix(name);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return applyPrefix(name);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return name == null ? null : applyPrefix(name);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return name == null ? null : Identifier.toIdentifier(toSnakeCase(name.getText()));
    }

    private Identifier applyPrefix(Identifier identifier) {
        return Identifier.toIdentifier(CUSTOM_TABLE_NAME_PREFIX + toSnakeCase(identifier.getText()));
    }

    private String toSnakeCase(String name) {
        if (name == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (char character : name.toCharArray()) {
            if (Character.isUpperCase(character)) {
                result.append(StringValues.UNDER_SCORE).append(Character.toLowerCase(character));
            } else {
                result.append(character);
            }
        }
        return result.toString().startsWith(StringValues.UNDER_SCORE) ? result.substring(1) : result.toString();
    }
}
