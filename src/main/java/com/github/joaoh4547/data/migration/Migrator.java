package com.github.joaoh4547.data.migration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface representing a migrator that is responsible for migrating data in a database.
 */
public interface Migrator {

    String MIGRATION_TABLE = "TM_MIGRATION";

    /**
     * Migrates data in a database using the provided Connection.
     *
     * @param connection the connection to the database
     */
    boolean migrate(Connection connection);

    /**
     * Checks if the data has already been migrated.
     *
     * @return true if the data has already been migrated, false otherwise
     */
    boolean isAlreadyMigrated();

    /**
     * Retrieves the name of the migrator.
     *
     * @return the name of the migrator
     */
    String getName();


    void insertMigration(Connection connection, MigrationStatus status);

    void updateStatus(Connection connection, MigrationStatus status);

    boolean existsMigration(Connection connection);
}
