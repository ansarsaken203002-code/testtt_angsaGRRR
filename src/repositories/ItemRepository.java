package repositories;

import models.Item;
import connectim_bazuuu.DBConnection;
import repositories.interfaces.ItemRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemRepository implements ItemRepositoryInterface {
    private final DBConnection conn;

    public ItemRepository(DBConnection conn) {
        this.conn = conn;
    }

    public List<Item> loadItems() throws Exception {
        ArrayList<Item> items = new ArrayList<>();
        Connection connek = conn.getConnection();
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

        return items;
    }

    public void saveItems(List<Item> items) throws Exception {
        Connection connek = conn.getConnection();
        Statement stata = connek.createStatement();
        for(Item i : items){
            stata.executeUpdate(
                    "UPDATE school_items SET quantity = " + i.getQuantity() + " WHERE id = " + i.getId()
            );
        }
        stata.close();
    }

    public Item getItemByID(int id) throws Exception {
        Item item = null;
        Connection connek = conn.getConnection();
        PreparedStatement stata = connek.prepareStatement("SELECT id, item_name, price, quantity FROM school_items WHERE id=?");
        stata.setInt(1, id);
        ResultSet rs = stata.executeQuery();
        if(rs.next()){
            item = new Item(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
            );
        }
        rs.close();
        stata.close();

        return item;
    }

    public Item getItemByName(String name) throws Exception {
        Item item = null;
        Connection connek = conn.getConnection();
        PreparedStatement stata = connek.prepareStatement("SELECT id, item_name, price, quantity FROM school_items WHERE item_name=?");
        stata.setString(1, name);
        ResultSet rs = stata.executeQuery();
        if(rs.next()){
            item = new Item(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
            );
        }
        rs.close();
        stata.close();

        return item;
    }
}
