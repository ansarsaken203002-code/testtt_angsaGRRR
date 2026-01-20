package modeli;

public class Order {

    private String itemName;
    private int quantity;
    private double totalPrice;

    public Order(String itemName, int quantity, double totalPrice){
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
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
