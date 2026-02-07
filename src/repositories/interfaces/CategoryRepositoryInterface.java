package repositories.interfaces;

import models.Category;
import java.util.List;

public interface CategoryRepositoryInterface {
    List<Category> getAllCategories() throws Exception;
    Category getCategoryById(int id) throws Exception;
    Category getCategoryByName(String name) throws Exception;
    void addCategory(Category category) throws Exception;
    void updateCategory(Category category) throws Exception;
}