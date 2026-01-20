package r.e.p.o;

import modeli.Item;
import connectim_bazuuu.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemRepository {

    public ArrayList<Item> loadItems() throws Exception {
        ArrayList<Item> items = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM school_items ORDER BY id");
        while(rs.next()){
            items.add(new Item(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
            ));
        }
        rs.close();
        st.close();
        con.close();
        return items;
    }

    public void saveItems(ArrayList<Item> items) throws Exception {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        for(Item i : items){
            st.executeUpdate(
                    "UPDATE school_items SET quantity = " + i.quantity + " WHERE id = " + i.id
            );
        }
        st.close();
        con.close();
    }
}
