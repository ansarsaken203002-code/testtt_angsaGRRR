package services;

import modeli.Item;
import java.util.ArrayList;

public class ItemService {

    private final ArrayList<Item> items;

    public ItemService(ArrayList<Item> items){
        this.items = items;
    }

    public void showItems(){
        for(Item i : items){
            System.out.println(
                    i.getId() + " " +
                            i.getName() + " " +
                            i.getPrice() + " " +
                            i.getQuantity()
            );
        }
    }

    public Item findById(int id){
        for(Item i : items){
            if(i.getId() == id) return i;
        }
        return null;
    }

    public Item findByName(String name){
        for(Item i : items){
            if(i.getName().equals(name)) return i;
        }
        return null;
    }

    public ArrayList<Item> getItems(){
        return items;
    }
}
