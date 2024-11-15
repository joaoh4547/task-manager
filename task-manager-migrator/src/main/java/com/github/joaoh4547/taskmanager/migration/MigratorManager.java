package com.github.joaoh4547.taskmanager.migration;


import com.github.joaoh4547.taskmanager.db.DatabaseManager;
import com.github.joaoh4547.taskmanager.utils.ReflectionUtils;
import io.github.classgraph.ClassGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.stream.Stream;

/**
 * The MigratorManager class provides functionality to manage database migrations.
 * It allows running migrations and creating the migration table if it does not exist.
 */
public class MigratorManager {


    private static final Logger LOG = LoggerFactory.getLogger(MigratorManager.class);

    /**
     * Constant representing the name of the migration table in the database.
     * This table is used to track the status of database migrations.
     */
    public static final String MIGRATION_TABLE = Migrator.MIGRATION_TABLE;

    /**
     * This method is used to run any pending database migrations. It checks if the migration table exists in the
     * database,
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

            Collection<Migrator> migrators = new ArrayList<>();

            Collection<File> files = new ArrayList<>();
            Collection<URL> urls;
            Enumeration<URL> urlEnumeration = Thread.currentThread().getContextClassLoader().getResources("scripts");


            try (var scan = new ClassGraph().acceptPaths("scripts").scan()) {
                var resources = scan.getAllResources();
                urls = new ArrayList<>(resources.getURLs());
            }
            for (var url : urls) {
                Path path = Paths.get(url.toURI());
                try (Stream<Path> paths = Files.walk(path, 1)) {
                    paths.filter(Files::isRegularFile)
                            .forEach(filePath -> {
                                files.add(filePath.toFile());
                            });
                }
            }


            for (File file : files) {
                migrators.add(new ScriptMigrator(file));
            }

            ReflectionUtils.getSubclasses(JavaMigrator.class, false).forEach(c -> {
                JavaMigrator migrator = ReflectionUtils.newInstance(c);
                migrators.add(migrator);
            });

            Collection<Migrator> fitered = migrators.stream()
                    .filter(m -> !m.isAlreadyMigrated())
                    .sorted(Comparator.nullsLast(Comparator.comparing(Migrator::getName)))
                    .toList();


            LOG.info("Migrations to migrate [{}]",
                     String.join(", ", fitered.stream().map(Migrator::toString).toList()));


            boolean successMigrate = true;
            for (Migrator m : fitered) {
                boolean migrated = m.migrate(connection);
                if (!migrated) {
                    LOG.info("Migration failed");
                    successMigrate = false;
                    break;
                }
            }
            if (successMigrate) {
                LOG.info("Migrations completed");
            }
        }
        catch (Exception e) {
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
