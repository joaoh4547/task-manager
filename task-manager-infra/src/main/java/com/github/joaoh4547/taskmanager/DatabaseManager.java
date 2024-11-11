package com.github.joaoh4547.taskmanager;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class DatabaseManager provides methods to work with the database connection.
 */
public class DatabaseManager {

    /**
     * Returns the data source used for establishing a connection to the database.
     *
     * @return the HikariDataSource object representing the data source
     */
    public HikariDataSource getDataSource() {
        return DataBaseContext.getDataSource();
    }

    /**
     * Retrieves a database connection from the underlying data source.
     *
     * @return a Connection object representing the connection to the database
     * @throws SQLException if an error occurs while attempting to retrieve the connection
     */
    public static Connection getConnection() throws SQLException {
        return DataBaseContext.getDataSource().getConnection();
    }


}
