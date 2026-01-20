package services;

import connectim_bazuuu.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminService {

    public boolean login(String login, String pass) throws Exception {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT * FROM admin WHERE username = '" + login + "' AND password = '" + pass + "'"
        );
        boolean ok = rs.next();
        rs.close();
        st.close();
        con.close();
        return ok;
    }
}
