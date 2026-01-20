package services;

import modeli.Item;
import java.util.ArrayList;

public class ItemService {

    private ArrayList<Item> items;

    public ItemService(ArrayList<Item> items){
        this.items = items;
    }

    public void showItems(){
        for(Item i : items){
            System.out.println(i.id + " " + i.name + " " + i.price + " " + i.quantity);
        }
    }

    public Item findById(int id){
        for(Item i : items){
            if(i.id == id) return i;
        }
        return null;
    }

    public Item findByName(String name){
        for(Item i : items){
            if(i.name.equals(name)) return i;
        }
        return null;
    }

    public ArrayList<Item> getItems(){
        return items;
    }
}
