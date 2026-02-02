package repositories.interfaces;

import models.Item;

import java.util.List;

public interface ItemRepositoryInterface {
    List<Item> loadItems() throws Exception;
    void saveItems(List<Item> items) throws Exception;
    Item getItemByID(int id) throws Exception;
    Item getItemByName(String name) throws Exception;
}
