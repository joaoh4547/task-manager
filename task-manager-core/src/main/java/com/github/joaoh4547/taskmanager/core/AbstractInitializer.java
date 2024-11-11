package com.github.joaoh4547.taskmanager.core;



import com.github.joaoh4547.taskmanager.DataBaseContext;
import com.github.joaoh4547.taskmanager.core.migration.MigratorManager;
import com.github.joaoh4547.taskmanager.core.utils.Bundler;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * A class that defines the initialization process for the application.
 * It implements the Initializer interface, providing a method to initialize the application components.
 */
public class AbstractInitializer implements Initializer {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractInitializer.class);

    /**
     * Represents the bundle key used for obtaining database configuration values.
     */
    private static final String DATABASE_BUNDLE = "database";


    @Override
    public void onInitialize() {
        initDatabase();
        if (isRunMigrations()) {
            new MigratorManager().runMigrations();
        }
    }

    /**
     * Initializes the database connection using the configuration values from the specified bundle.
     * The JDBC URL, username, and password are retrieved from the "database" bundle.
     * Sets the initialized DataSource in the DataBaseContext for future database operations.
     */
    protected void initDatabase() {
        try {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(Bundler.getValue("database.url", DATABASE_BUNDLE));
            ds.setUsername(Bundler.getValue("database.user", DATABASE_BUNDLE));
            ds.setPassword(Bundler.getValue("database.password", DATABASE_BUNDLE));
            ds.setMaximumPoolSize(10);
            ds.setConnectionTimeout(30000);
            ds.setConnectionTestQuery("SELECT 1 from dual");
            DataBaseContext.setDataSource(ds);
            Connection c = ds.getConnection();
            c.close();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

    }

    /**
     * Checks if the migrations should be run during initialization.
     *
     * @return true if migrations should be run, false otherwise
     */
    protected boolean isRunMigrations() {
        return true;
    }
}
