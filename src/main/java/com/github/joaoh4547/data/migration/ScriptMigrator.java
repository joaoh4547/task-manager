package com.github.joaoh4547.data.migration;

import com.github.joaoh4547.data.DatabaseManager;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class ScriptMigrator implements the Migrator interface and provides migration functionality for scripts.
 */
public class ScriptMigrator implements Migrator {


    /**
     * Represents the file that has been migrated or is to be migrated.
     * This variable is used to store the file object representing the script file
     * that is being migrated within the context of script migration functionality.
     */
    private final File migratedFile;


    public ScriptMigrator(File migratedFile) {
        this.migratedFile = migratedFile;
    }


    @Override
    public void migrate(Connection connection) throws SQLException {

    }

    @Override
    public boolean isAlreadyMigrated() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseManager.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + MIGRATION_TABLE + " WHERE MIGRATION_NAME =? AND MIGRATION_STATUS IN (?,?)");
            statement.setString(1, this.migratedFile.getName());
            statement.setInt(2, MigrationStatus.ERROR.getCode());
            statement.setInt(3, MigrationStatus.PENDING.getCode());
            resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ignored) {

        }
        return false;
    }
}
