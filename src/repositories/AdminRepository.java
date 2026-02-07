package repositories;

import models.Admin;
import connectim_bazuuu.DBConnection;
import repositories.interfaces.AdminRepositoryInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository implements AdminRepositoryInterface {
    private DBConnection conn;

    public AdminRepository(DBConnection conn) {
        this.conn = conn;
    }

    public Admin getAdminByCredentials(String username, String password) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "SELECT id, username, password, role FROM admin WHERE username = ? AND password = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        Admin admin = null;
        if (rs.next()) {
            admin = new Admin(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
            );
        }

        rs.close();
        stmt.close();
        return admin;
    }

    public Admin getAdminById(int id) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "SELECT id, username, password, role FROM admin WHERE id = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        Admin admin = null;
        if (rs.next()) {
            admin = new Admin(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
            );
        }

        rs.close();
        stmt.close();
        return admin;
    }

    public void createAdmin(Admin admin) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "INSERT INTO admin (username, password, role) VALUES (?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, admin.getUsername());
        stmt.setString(2, admin.getPassword());
        stmt.setString(3, admin.getRole());
        stmt.executeUpdate();

        stmt.close();
    }

    public List<Admin> getAllAdmins() throws Exception {
        List<Admin> admins = new ArrayList<>();
        Connection connection = conn.getConnection();
        String sql = "SELECT id, username, password, role FROM admin ORDER BY id";

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Admin admin = new Admin(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
            );
            admins.add(admin);
        }

        rs.close();
        stmt.close();
        return admins;
    }
}