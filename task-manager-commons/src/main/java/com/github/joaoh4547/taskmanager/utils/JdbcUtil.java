package com.github.joaoh4547.taskmanager.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcUtil.class);

    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void close(Statement stmt) {
        try {
            stmt.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void close(Statement stmt, ResultSet rs) {
        close(stmt);
        close(rs);
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        close(stmt, rs);
        close(conn);
    }
}
