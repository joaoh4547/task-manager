package com.github.joaoh4547;

import com.github.joaoh4547.data.DataBaseContext;
import com.github.joaoh4547.utils.Bundler;
import com.zaxxer.hikari.HikariDataSource;

/**
 * A class that defines the initialization process for the application.
 * It implements the Initializer interface, providing a method to initialize the application components.
 */
public class AbstractInitializer implements Initializer {

    /**
     * Represents the bundle key used for obtaining database configuration values.
     */
    private static final String DATABASE_BUNDLE = "database";


    @Override
    public void onInitialize() {
        initDatabase();
    }

    /**
     * Initializes the database connection using the configuration values from the specified bundle.
     * The JDBC URL, username, and password are retrieved from the "database" bundle.
     * Sets the initialized DataSource in the DataBaseContext for future database operations.
     */
    protected void initDatabase() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(Bundler.getValue("database.url", DATABASE_BUNDLE));
        ds.setUsername(Bundler.getValue("database.user", DATABASE_BUNDLE));
        ds.setPassword(Bundler.getValue("database.password", DATABASE_BUNDLE));
        DataBaseContext.setDataSource(ds);
    }

    /**
     * Checks if the migrations should be run during initialization.
     *
     * @return true if migrations should be run, false otherwise
     */
    protected boolean isRunMigrations(){
        return true;
    }
}
