package services;

import models.Item;
import repositories.interfaces.ItemRepositoryInterface;

import java.util.List;

public class ItemService {
    private final ItemRepositoryInterface repo;

    public ItemService(ItemRepositoryInterface repo){
        this.repo = repo;
    }

    public List<Item> getItems() throws Exception {
        return repo.loadItems();
    }

    public Item findById(int id) throws Exception {
        return repo.getItemByID(id);
    }

    public Item findByName(String name) throws Exception {
        return repo.getItemByName(name);
    }
}
