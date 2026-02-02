package connectim_bazuuu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;

    public Connection getConnection() throws Exception {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        String url = "jdbc:postgresql://localhost:5432/school_order";
        String user = "postgres";
        String password = "0000";
        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection(url, user, password);
    }

    public void close() throws SQLException {
        if (connection == null || connection.isClosed()) {
            return;
        }

        connection.close();
    }
}