package modeli;

public class Item {

    private int id;
    private String name;
    private double price;
    private int quantity;

    public Item(int id, String name, double price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public void decreaseQuantity(int q){
        quantity -= q;
    }

    public void increaseQuantity(int q){
        quantity += q;
    }
}
