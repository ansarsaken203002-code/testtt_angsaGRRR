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
        Connection connek = DBConnection.getConnection();
        Statement stata = connek.createStatement();
        ResultSet rs = stata.executeQuery("SELECT * FROM school_items ORDER BY id");
        while(rs.next()){
            items.add(new Item(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
            ));
        }
        rs.close();
        stata.close();
        connek.close();
        return items;
    }

    public void saveItems(ArrayList<Item> items) throws Exception {
        Connection connek = DBConnection.getConnection();
        Statement stata = connek.createStatement();
        for(Item i : items){
            stata.executeUpdate(
                    "UPDATE school_items SET quantity = " + i.getQuantity() + " WHERE id = " + i.getId()
            );
        }
        stata.close();
        connek.close();
    }
}
