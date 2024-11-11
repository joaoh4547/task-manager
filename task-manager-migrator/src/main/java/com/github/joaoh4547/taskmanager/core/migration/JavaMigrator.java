package com.github.joaoh4547.taskmanager.core.migration;

/**
 * This abstract class represents a JavaMigrator that implements the Migrator interface.
 * It provides common functionality for migrating data in a database using a Connection object.
 */
public abstract class JavaMigrator extends AbstractMigrator {

    @Override
    public String getName() {
        return getClass().getName();
    }

}
