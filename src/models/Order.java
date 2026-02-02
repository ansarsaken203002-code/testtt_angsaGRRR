package models;

public class Order {
    private int id;
    private String itemName;
    private int quantity;
    private double totalPrice;

    public Order(String itemName, int quantity, double totalPrice){
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Order(int id, String itemName, int quantity, double totalPrice){
        this(itemName, quantity, totalPrice);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getItemName(){
        return itemName;
    }

    public int getQuantity(){
        return quantity;
    }

    public double getTotalPrice(){
        return totalPrice;
    }
}
