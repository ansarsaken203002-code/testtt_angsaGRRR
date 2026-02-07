package services.interfaces;

import models.Item;
import java.util.List;

public interface ItemServiceInterface {
    List<Item> getItems() throws Exception;
    List<Item> getItemsByCategory(int categoryId) throws Exception;
    Item findById(int id) throws Exception;
    Item findByName(String name) throws Exception;
    void addItem(Item item) throws Exception;
    void updateItem(Item item) throws Exception;
    void deleteItem(int id) throws Exception;
}