package r.e.p.o;

import modeli.Order;
import connectim_bazuuu.DBConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderRepository {

    public void saveOrders(ArrayList<Order> orders) throws Exception {
        Connection connek = DBConnection.getConnection();
        Statement stata = connek.createStatement();
        for(Order o : orders){
            stata.executeUpdate(
                    "INSERT INTO orders (item_name, quantity, total_price) VALUES (" +
                            "'" + o.getItemName() + "', " + o.getQuantity() + ", " + o.getTotalPrice() + ")"
            );
        }
        stata.close();
        connek.close();
    }
}
