package r.e.p.o;

import modeli.Order;
import connectim_bazuuu.DBConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderRepository {

    public void saveOrders(ArrayList<Order> orders) throws Exception {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        for(Order o : orders){
            st.executeUpdate(
                    "INSERT INTO orders (item_name, quantity, total_price) VALUES (" +
                            "'" + o.itemName + "', " + o.quantity + ", " + o.totalPrice + ")"
            );
        }
        st.close();
        con.close();
    }
}
