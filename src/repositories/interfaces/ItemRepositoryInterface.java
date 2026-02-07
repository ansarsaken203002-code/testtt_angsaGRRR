package repositories.interfaces;

import models.Item;
import java.util.List;

public interface ItemRepositoryInterface {
    List<Item> loadItems() throws Exception;
    List<Item> loadItemsByCategory(int categoryId) throws Exception;
    void saveItems(List<Item> items) throws Exception;
    Item getItemByID(int id) throws Exception;
    Item getItemByName(String name) throws Exception;
    void addItem(Item item) throws Exception;
    void updateItem(Item item) throws Exception;
    void deleteItem(int id) throws Exception;
}