package com.github.joaoh4547.taskmanager.db;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Class representing the context for managing the database connection.
 */
public class DataBaseContext {

    /**
     * InheritableThreadLocal variable that stores a HikariDataSource object representing the database connection for the current thread's context.
     */
    private static final InheritableThreadLocal<HikariDataSource> dataSource = new InheritableThreadLocal<>();

    /**
     * Retrieve the HikariDataSource object representing the database connection for the current thread's context.
     *
     * @return the HikariDataSource object stored in an InheritableThreadLocal variable
     */
    public static HikariDataSource getDataSource() {
        return dataSource.get();
    }

    /**
     * Sets the provided HikariDataSource object as the data source for the current thread's context.
     *
     * @param ds the HikariDataSource object to be set as the data source
     */
    public static void setDataSource(HikariDataSource ds) {
        dataSource.set(ds);
    }


}
