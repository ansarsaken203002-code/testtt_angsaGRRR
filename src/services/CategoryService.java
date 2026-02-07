package services;

import models.Category;
import repositories.interfaces.CategoryRepositoryInterface;
import services.interfaces.CategoryServiceInterface;
import utils.Validator;
import java.util.List;

public class CategoryService implements CategoryServiceInterface {
    private CategoryRepositoryInterface repo;

    public CategoryService(CategoryRepositoryInterface repo) {
        this.repo = repo;
    }

    public List<Category> getAllCategories() throws Exception {
        return repo.getAllCategories();
    }

    public Category getCategoryById(int id) throws Exception {
        return repo.getCategoryById(id);
    }

    public Category getCategoryByName(String name) throws Exception {
        return repo.getCategoryByName(name);
    }

    public void addCategory(String name, String description) throws Exception {
        if (!Validator.isNotEmpty(name)) {
            throw new Exception("Category name cannot be empty");
        }

        if (!Validator.isNotEmpty(description)) {
            throw new Exception("Category description cannot be empty");
        }

        Category category = new Category(name, description);
        repo.addCategory(category);
    }

    public void updateCategory(Category category) throws Exception {
        if (!Validator.isNotEmpty(category.getName())) {
            throw new Exception("Category name cannot be empty");
        }

        if (!Validator.isNotEmpty(category.getDescription())) {
            throw new Exception("Category description cannot be empty");
        }

        repo.updateCategory(category);
    }
}