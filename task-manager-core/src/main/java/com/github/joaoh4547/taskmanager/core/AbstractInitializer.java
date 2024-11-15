package com.github.joaoh4547.taskmanager.core;


import com.github.joaoh4547.taskmanager.db.DataBaseContext;
import com.github.joaoh4547.taskmanager.db.JpaManager;
import com.github.joaoh4547.taskmanager.migration.MigratorManager;
import com.github.joaoh4547.taskmanager.utils.Bundler;
import com.zaxxer.hikari.HikariConfig;
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
        initJPA();
        if (isRunMigrations()) {
            new MigratorManager().runMigrations();
        }
    }

    private void initJPA() {
        LOG.info("Initializing JPA Manager");
        JpaManager.getInstance().init();
    }

    /**
     * Initializes the database connection using the configuration values from the specified bundle.
     * The JDBC URL, username, and password are retrieved from the "database" bundle.
     * Sets the initialized DataSource in the DataBaseContext for future database operations.
     */
    protected void initDatabase() {
        try {


            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(Bundler.getValue("database.url", DATABASE_BUNDLE));
            config.setUsername(Bundler.getValue("database.user", DATABASE_BUNDLE));
            config.setPassword(Bundler.getValue("database.password", DATABASE_BUNDLE));
            config.setMaximumPoolSize(30);
            config.addDataSourceProperty("dataSource.logLevel", "INFO");
            config.setLeakDetectionThreshold(10000);
            config.setMinimumIdle(10);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setLeakDetectionThreshold(15000);
            config.setConnectionTestQuery("SELECT 1 from dual");

            HikariDataSource ds = new HikariDataSource(config);

            DataBaseContext.setDataSource(ds);
            try (Connection con = ds.getConnection()) {
                if (con.isValid(2)) { // Testa a conexão por 2 segundos
                    LOG.info("Database connection established");
                }
                else {
                    LOG.info("Database connection failed");
                }
            }
            catch (Exception e) {
                ds.close();
                LOG.error("Database connection failed", e);
            }

        }
        catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
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
