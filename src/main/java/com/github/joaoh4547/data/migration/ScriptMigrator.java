package com.github.joaoh4547.data.migration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class ScriptMigrator implements the Migrator interface and provides migration functionality for scripts.
 */
public class ScriptMigrator extends AbstractMigrator {


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
    public String getName() {
        return migratedFile.getName().substring(0, migratedFile.getName().lastIndexOf('.'));
    }

    @Override
    protected void doMigrate(Connection connection) throws SQLException {

        try (BufferedReader reader = new BufferedReader(new FileReader(migratedFile))) {
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.trim().startsWith("--")) {
                    continue;
                }
                sqlBuilder.append(line);
                if (line.trim().endsWith(";")) {

                    String sql = sqlBuilder.toString().trim();
                    sql = sql.substring(0, sql.length() - 1);  // Remover o ';' final


                    try (Statement statement = connection.createStatement()) {
                        statement.execute(sql);
                    }
                    sqlBuilder.setLength(0);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String toString() {
        return migratedFile.getName();
    }
}
