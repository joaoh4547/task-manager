package com.github.joaoh4547.data.migration;

import com.github.joaoh4547.data.DataBaseContext;
import com.github.joaoh4547.data.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The MigratorManager class provides functionality to manage database migrations.
 * It allows running migrations and creating the migration table if it does not exist.
 */
public class MigratorManager {

    /**
     * Constant representing the name of the migration table in the database.
     * This table is used to track the status of database migrations.
     */
    public static final String MIGRATION_TABLE = Migrator.MIGRATION_TABLE;

    /**
     * This method is used to run any pending database migrations. It checks if the migration table exists in the database,
     * and if it does not, it creates the migration table. It utilizes DatabaseContext to get the database connection.
     *
     * @throws MigrationException if any exception occurs during the migration process
     */
    public void runMigrations() {
        try {
            Connection connection = DatabaseManager.getConnection();
            if (!isTableExists(connection)) {
                createMigrationTable(connection);
            }
        } catch (Exception e) {
            throw new MigrationException(e.getMessage(), e);
        }
    }

    /**
     * Creates the migration table in the database if it does not already exist.
     *
     * @param connection the database connection to execute the SQL statement
     * @throws SQLException if an error occurs while creating the migration table
     */
    private void createMigrationTable(Connection connection) throws SQLException {
        String sql = """
                create table TM_MIGRATION(
                    MIGRATION_ID NUMBER GENERATED as identity,
                    MIGRATION_NAME VARCHAR2(500) not null,
                    MIGRATION_STATUS NUMBER(1) not null,
                    MIGRATION_CREATED_AT DATE default sysdate,
                    MIGRATION_UPDATED_AT DATE default sysdate,
                    CONSTRAINT PK_TM_MIGRATION PRIMARY KEY(MIGRATION_ID)\s
                )""";
        connection.prepareStatement(sql).execute();
    }


    /**
     * Checks if a specified table exists in the database.
     *
     * @param connection the database connection to query for table existence
     * @return true if the table exists, false otherwise
     * @throws SQLException if an error occurs while accessing the database metadata
     */
    private boolean isTableExists(Connection connection) throws SQLException {
        var metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, MigratorManager.MIGRATION_TABLE, null);
        return tables.next();
    }

}
