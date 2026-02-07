package services;

import models.Item;
import repositories.interfaces.ItemRepositoryInterface;
import services.interfaces.ItemServiceInterface;
import utils.Validator;
import java.util.List;

public class ItemService implements ItemServiceInterface {
    private ItemRepositoryInterface repo;

    public ItemService(ItemRepositoryInterface repo) {
        this.repo = repo;
    }

    public List<Item> getItems() throws Exception {
        return repo.loadItems();
    }

    public List<Item> getItemsByCategory(int categoryId) throws Exception {
        return repo.loadItemsByCategory(categoryId);
    }

    public Item findById(int id) throws Exception {
        return repo.getItemByID(id);
    }

    public Item findByName(String name) throws Exception {
        return repo.getItemByName(name);
    }

    public void addItem(Item item) throws Exception {
        if (!Validator.isValidItemName(item.getName())) {
            throw new Exception("Item name must be between 2 and 100 characters");
        }

        if (!Validator.isValidPrice(item.getPrice())) {
            throw new Exception("Price must be positive");
        }

        if (item.getQuantity() < 0) {
            throw new Exception("Quantity cannot be negative");
        }

        repo.addItem(item);
    }

    public void updateItem(Item item) throws Exception {
        if (!Validator.isValidItemName(item.getName())) {
            throw new Exception("Item name must be between 2 and 100 characters");
        }

        if (!Validator.isValidPrice(item.getPrice())) {
            throw new Exception("Price must be positive");
        }

        if (item.getQuantity() < 0) {
            throw new Exception("Quantity cannot be negative");
        }

        repo.updateItem(item);
    }

    public void deleteItem(int id) throws Exception {
        repo.deleteItem(id);
    }
}