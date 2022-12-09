package ua.knu.ynortman.key;

import lombok.extern.slf4j.Slf4j;
import ua.knu.ynortman.key.utils.FileUtils;

import java.math.BigInteger;
import java.sql.*;
import java.util.Arrays;

@Slf4j
public class DBKeyManagement {

    private static final String GET_KEYS_QUERY = "SELECT left(prime, -2) \n" +
            "FROM primes \n" +
            "WHERE id IS NULL \n" +
            "LIMIT ?";

    private static final String GET_KEYS_BY_ID_QUERY = "SELECT left(prime, -2) \n" +
            "FROM primes \n" +
            "WHERE id = ?";

    public static BigInteger[] getKeysForReceiver(int n, String senderId) {
        BigInteger[] keys = new BigInteger[n];
        Connection conn = FileUtils.getConnection();
        try {

            String deleteSql = "DELETE FROM primes WHERE id = '" + senderId + "'";
            log.debug(deleteSql);
            PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
            deleteStatement.executeUpdate();

            PreparedStatement statement = conn.prepareStatement(GET_KEYS_QUERY);
            statement.setInt(1, n);
            ResultSet rs = statement.executeQuery();
            int i = 0;
            while (rs.next()) {
                keys[i] = new BigInteger(rs.getString(1));
                i++;
            }

            StringBuilder inClause = new StringBuilder();
            for (i = 0; i < n; ++i) {
                inClause.append("'").append(keys[i].toString()).append(",,'");
                if (i != n - 1) {
                    inClause.append(", ");
                }
            }
            String sql = "UPDATE primes  SET id = '" + senderId + "' WHERE prime in (" + inClause + ")";
            log.debug(sql);
            PreparedStatement updateStatement = conn.prepareStatement(sql);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return keys;
    }

    public static BigInteger[] getKeysForSender(int n, String receiverId) {
        BigInteger[] keys = new BigInteger[n];
        Connection conn = FileUtils.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(GET_KEYS_BY_ID_QUERY);
            statement.setString(1, receiverId);
            ResultSet rs = statement.executeQuery();
            int i = 0;
            while (rs.next()) {
                keys[i] = new BigInteger(rs.getString(1));
                i++;
            }

            String sql = "DELETE FROM primes WHERE id = '" + receiverId + "'";
            log.debug(sql);
            PreparedStatement updateStatement = conn.prepareStatement(sql);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return keys;
    }
}
