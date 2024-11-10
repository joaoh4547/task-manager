package com.github.joaoh4547.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class responsible for generating keys for different tables using a custom sequence table in the database.
 */
public class KeyGenerator {

    /**
     * Represents the name of the table used for storing sequence values.
     */
    private static final String SEQUENCE_TABLE = "tm_sequence";

    /**
     * Represents the name of the column in the table.
     */
    private static final String TABLE_COLUMN = "table_name";

    /**
     * Represents the name of the column in the sequence table that stores the next key value.
     */
    private static final String KEY_COLUMN = "next_key";

    /**
     * Generates a unique key for the specified table based on a custom sequence in the database.
     *
     * @param table The name of the table for which a key needs to be generated.
     * @param initialId The initial value to use if no sequence exists for the table.
     * @return The generated key as a Long value. Returns null if an SQLException occurs during the process.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public Long generateKey(String table, long initialId) throws SQLException {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DataBaseContext.getDataSource().getConnection();

            String searchSequenceSql = "select  1 from tm_sequence where table_name = ?";

            ps = con.prepareStatement(searchSequenceSql);

            boolean existsSequence;

            ps.setString(1, table);
            rs = ps.executeQuery();
            existsSequence = rs.next();

            if (existsSequence) {
                String updateSequenceSql = "update tm_sequence set sequence = sequence + 1 where table_name = ?";
                ps = con.prepareStatement(updateSequenceSql);
                ps.setString(1, table);
                ps.executeUpdate();

                ps = con.prepareStatement("select next_key from tm_sequence where table_name = ?");
                ps.setString(1, table);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getLong(1);
                }

            } else {
                String insertSequenceSql = "insert into tm_sequence (table_name, sequence) values (?, ?)";
                ps = con.prepareStatement(insertSequenceSql);
                ps.setString(1, table);
                ps.setLong(2, initialId);
                ps.executeUpdate();
                return initialId;
            }

        } catch (SQLException e) {
            return null;
        } finally {
            assert ps != null;
            ps.close();
            assert rs != null;
            rs.close();
            con.close();
        }

        return 0L;

    }

}
