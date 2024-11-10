package com.github.joaoh4547.data;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    public HikariDataSource getDataSource() {
        return DataBaseContext.getDataSource();
    }

    public static Connection getConnection() throws SQLException {
        return DataBaseContext.getDataSource().getConnection();
    }


}
