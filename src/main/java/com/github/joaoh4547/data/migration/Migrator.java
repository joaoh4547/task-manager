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
     * @throws SQLException if a database error occurs during migration
     */
    void migrate(Connection connection) throws SQLException;

    /**
     * Checks if the data has already been migrated.
     *
     * @return true if the data has already been migrated, false otherwise
     */
    boolean isAlreadyMigrated();

}
