package repositories;

import models.Category;
import connectim_bazuuu.DBConnection;
import repositories.interfaces.CategoryRepositoryInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements CategoryRepositoryInterface {
    private DBConnection conn;

    public CategoryRepository(DBConnection conn) {
        this.conn = conn;
    }

    public List<Category> getAllCategories() throws Exception {
        List<Category> categories = new ArrayList<>();
        Connection connection = conn.getConnection();
        String sql = "SELECT id, name, description FROM categories ORDER BY id";

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Category category = new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description")
            );
            categories.add(category);
        }

        rs.close();
        stmt.close();
        return categories;
    }

    public Category getCategoryById(int id) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "SELECT id, name, description FROM categories WHERE id = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        Category category = null;
        if (rs.next()) {
            category = new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description")
            );
        }

        rs.close();
        stmt.close();
        return category;
    }

    public Category getCategoryByName(String name) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "SELECT id, name, description FROM categories WHERE name = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();

        Category category = null;
        if (rs.next()) {
            category = new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description")
            );
        }

        rs.close();
        stmt.close();
        return category;
    }

    public void addCategory(Category category) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, category.getName());
        stmt.setString(2, category.getDescription());
        stmt.executeUpdate();

        stmt.close();
    }

    public void updateCategory(Category category) throws Exception {
        Connection connection = conn.getConnection();
        String sql = "UPDATE categories SET name = ?, description = ? WHERE id = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, category.getName());
        stmt.setString(2, category.getDescription());
        stmt.setInt(3, category.getId());
        stmt.executeUpdate();

        stmt.close();
    }
}