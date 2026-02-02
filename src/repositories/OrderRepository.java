package repositories;

import models.Item;
import models.Order;
import connectim_bazuuu.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private final DBConnection conn;

    public OrderRepository(DBConnection conn) {
        this.conn = conn;
    }

    public void saveOrders(List<Order> orders) throws Exception {
        Connection connek = conn.getConnection();
        Statement stata = connek.createStatement();
        for(Order o : orders){
            stata.executeUpdate(
                    "INSERT INTO orders (item_name, quantity, total_price) VALUES (" +
                            "'" + o.getItemName() + "', " + o.getQuantity() + ", " + o.getTotalPrice() + ")"
            );
        }
        stata.close();
    }

    public List<Order> getOrders() throws Exception {
        ArrayList<Order> orders = new ArrayList<>();
        Connection connek = conn.getConnection();
        Statement stata = connek.createStatement();
        ResultSet rs = stata.executeQuery("SELECT id, item_name, quantity, total_price FROM orders ORDER BY id");
        while(rs.next()){
            orders.add(new Order(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("total_price")
            ));
        }
        rs.close();
        stata.close();

        return orders;
    }
}
