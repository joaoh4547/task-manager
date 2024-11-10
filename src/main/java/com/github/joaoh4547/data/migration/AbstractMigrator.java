package com.github.joaoh4547.data.migration;

import com.github.joaoh4547.data.DatabaseManager;
import com.github.joaoh4547.data.JdbcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public abstract class AbstractMigrator implements Migrator {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractMigrator.class);


    protected abstract void doMigrate(Connection connection) throws SQLException;

    @Override
    public boolean migrate(Connection connection) {
        boolean migrated = true;
        boolean existsMigrated = existsMigration(connection);
        MigrationStatus status = null;
        try {
            doMigrate(connection);
            status = MigrationStatus.SUCCESS;
        } catch (Exception e) {
            status = MigrationStatus.ERROR;
            LOG.error("Migration failed", e);
            migrated = false;
        } finally {
            if (existsMigrated) {
                updateStatus(connection, status);
            } else {
                insertMigration(connection, status);
            }
        }

        if (migrated) {
            LOG.info("Migration {} was successful", this);
        }

        return migrated;
    }

    @Override
    public boolean isAlreadyMigrated() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseManager.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + MIGRATION_TABLE + " WHERE MIGRATION_NAME =? AND MIGRATION_STATUS  = ?");
            statement.setString(1, getName());
            statement.setInt(2, MigrationStatus.SUCCESS.getCode());
            resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ignored) {

        }
        return false;
    }


    @Override
    public void insertMigration(Connection connection, MigrationStatus status) {
        PreparedStatement ps = null;

        try {
            String sql = "insert into " + MIGRATION_TABLE + "(MIGRATION_NAME, MIGRATION_STATUS) values (?,?)";

            ps = connection.prepareStatement(sql);
            ps.setString(1, getName());
            ps.setInt(2, status.getCode());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            JdbcUtil.close(ps);
        }
    }

    @Override
    public void updateStatus(Connection connection, MigrationStatus status) {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE " + MIGRATION_TABLE + " set MIGRATION_STATUS =?, MIGRATION_UPDATED_AT =? WHERE MIGRATION_NAME =?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, status.getCode());
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setString(3, getName());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            JdbcUtil.close(ps);
        }
    }

    @Override
    public boolean existsMigration(Connection connection) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM " + MIGRATION_TABLE + " WHERE MIGRATION_NAME =?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, getName());
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            JdbcUtil.close(ps, rs);
        }

        return false;
    }

    @Override
    public String toString() {
        return getName();
    }
}
