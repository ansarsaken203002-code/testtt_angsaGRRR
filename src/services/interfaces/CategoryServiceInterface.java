package services.interfaces;

import models.Category;
import java.util.List;

public interface CategoryServiceInterface {
    List<Category> getAllCategories() throws Exception;
    Category getCategoryById(int id) throws Exception;
    Category getCategoryByName(String name) throws Exception;
    void addCategory(String name, String description) throws Exception;
    void updateCategory(Category category) throws Exception;
}