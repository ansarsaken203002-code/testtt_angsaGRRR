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
    private DBConnection conn;

    public ItemRepository(DBConnection conn) {
        this.conn = conn;
    }

    public List<Item> loadItems() throws Exception {
        List<Item> items = new ArrayList<>();
        Connection connection = conn.getConnection();

        String sql = "SELECT i.id, i.item_name, i.price, i.quantity, i.category_id, c.name as category_name " +
                "FROM school_items i " +
                "LEFT JOIN categories c ON i.category_id = c.id " +
                "ORDER BY i.id";

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Item item = new Item(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getInt("category_id"),
                    rs.getString("category_name")
            );
            items.add(item);
        }

        rs.close();
        stmt.close();
        return items;
    }

    public List<Item> loadItemsByCategory(int categoryId) throws Exception {
        List<Item> items = new ArrayList<>();
        Connection connection = conn.getConnection();

        String sql = "SELECT i.id, i.item_name, i.price, i.quantity, i.category_id, c.name as category_name " +
                "FROM school_items i " +
                "LEFT JOIN categories c ON i.category_id = c.id " +
                "WHERE i.category_id = ? " +
                "ORDER BY i.id";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, categoryId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Item item = new Item(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getInt("category_id"),
                    rs.getString("category_name")
            );
            items.add(item);
        }

        rs.close();
        stmt.close();
        return items;
    }

    public void saveItems(List<Item> items) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "UPDATE school_items SET quantity = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);

        for (Item item : items) {
            stmt.setInt(1, item.getQuantity());
            stmt.setInt(2, item.getId());
            stmt.executeUpdate();
        }

        stmt.close();
    }

    public Item getItemByID(int id) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "SELECT i.id, i.item_name, i.price, i.quantity, i.category_id, c.name as category_name " +
                "FROM school_items i " +
                "LEFT JOIN categories c ON i.category_id = c.id " +
                "WHERE i.id = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        Item item = null;
        if (rs.next()) {
            item = new Item(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getInt("category_id"),
                    rs.getString("category_name")
            );
        }

        rs.close();
        stmt.close();
        return item;
    }

    public Item getItemByName(String name) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "SELECT i.id, i.item_name, i.price, i.quantity, i.category_id, c.name as category_name " +
                "FROM school_items i " +
                "LEFT JOIN categories c ON i.category_id = c.id " +
                "WHERE i.item_name = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();

        Item item = null;
        if (rs.next()) {
            item = new Item(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getInt("category_id"),
                    rs.getString("category_name")
            );
        }

        rs.close();
        stmt.close();
        return item;
    }

    public void addItem(Item item) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "INSERT INTO school_items (item_name, price, quantity, category_id) VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, item.getName());
        stmt.setDouble(2, item.getPrice());
        stmt.setInt(3, item.getQuantity());
        stmt.setInt(4, item.getCategoryId());
        stmt.executeUpdate();

        stmt.close();
    }

    public void updateItem(Item item) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "UPDATE school_items SET item_name = ?, price = ?, quantity = ?, category_id = ? WHERE id = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, item.getName());
        stmt.setDouble(2, item.getPrice());
        stmt.setInt(3, item.getQuantity());
        stmt.setInt(4, item.getCategoryId());
        stmt.setInt(5, item.getId());
        stmt.executeUpdate();

        stmt.close();
    }

    public void deleteItem(int id) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "DELETE FROM school_items WHERE id = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();

        stmt.close();
    }
}