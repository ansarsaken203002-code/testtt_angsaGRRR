package repositories;

import models.Order;
import connectim_bazuuu.DBConnection;
import repositories.interfaces.OrderRepositoryInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements OrderRepositoryInterface {
    private DBConnection conn;

    public OrderRepository(DBConnection conn) {
        this.conn = conn;
    }

    public void saveOrders(List<Order> orders, int adminId) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "INSERT INTO orders (item_name, quantity, total_price, admin_id, order_date) " +
                "VALUES (?, ?, ?, ?, NOW())";

        PreparedStatement stmt = connection.prepareStatement(sql);

        for (Order order : orders) {
            stmt.setString(1, order.getItemName());
            stmt.setInt(2, order.getQuantity());
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setInt(4, adminId);
            stmt.executeUpdate();
        }

        stmt.close();
    }

    public List<Order> getOrders() throws Exception {
        List<Order> orders = new ArrayList<>();
        Connection connection = conn.getConnection();

        String sql = "SELECT o.id, o.item_name, o.quantity, o.total_price, " +
                "TO_CHAR(o.order_date, 'YYYY-MM-DD HH24:MI:SS') as order_date, " +
                "o.admin_id, a.username " +
                "FROM orders o " +
                "LEFT JOIN admin a ON o.admin_id = a.id " +
                "ORDER BY o.id";

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Order order = new Order(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("total_price"),
                    rs.getString("order_date"),
                    rs.getInt("admin_id"),
                    rs.getString("username")
            );
            orders.add(order);
        }

        rs.close();
        stmt.close();
        return orders;
    }

    public List<Order> getOrdersByAdmin(int adminId) throws Exception {
        List<Order> orders = new ArrayList<>();
        Connection connection = conn.getConnection();

        String sql = "SELECT o.id, o.item_name, o.quantity, o.total_price, " +
                "TO_CHAR(o.order_date, 'YYYY-MM-DD HH24:MI:SS') as order_date, " +
                "o.admin_id, a.username " +
                "FROM orders o " +
                "LEFT JOIN admin a ON o.admin_id = a.id " +
                "WHERE o.admin_id = ? " +
                "ORDER BY o.id";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, adminId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Order order = new Order(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("total_price"),
                    rs.getString("order_date"),
                    rs.getInt("admin_id"),
                    rs.getString("username")
            );
            orders.add(order);
        }

        rs.close();
        stmt.close();
        return orders;
    }
}