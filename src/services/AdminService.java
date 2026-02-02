package services;

import connectim_bazuuu.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminService {
    private final DBConnection conn;

    public AdminService(DBConnection conn) {
        this.conn = conn;
    }

    public boolean login(String login, String pass) throws Exception {
        Connection connek = conn.getConnection();
        Statement stata = connek.createStatement();
        ResultSet rs = stata.executeQuery(
                "SELECT * FROM admin WHERE username = '" + login + "' AND password = '" + pass + "'"
        );
        boolean ok = rs.next();
        rs.close();
        stata.close();

        return ok;
    }
}
