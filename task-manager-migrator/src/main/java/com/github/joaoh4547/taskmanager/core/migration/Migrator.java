package com.github.joaoh4547.taskmanager.core.migration;

import java.sql.Connection;

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


    /**
     * Inserts a migration record into the database with the provided connection and migration status.
     *
     * @param connection the connection to the database
     * @param status the migration status to be inserted
     */
    void insertMigration(Connection connection, MigrationStatus status);

    /**
     * Updates the status of a migration in the database.
     *
     * @param connection the connection to the database
     * @param status the MigrationStatus to be updated
     */
    void updateStatus(Connection connection, MigrationStatus status);

    /**
     * Checks if a migration record already exists in the database for the current Migrator instance.
     *
     * @param connection the connection to the database for performing the check
     * @return true if a migration record with the same name exists, false otherwise
     */
    boolean existsMigration(Connection connection);
}
