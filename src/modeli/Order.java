package modeli;

public class Order {
    public String itemName;
    public int quantity;
    public double totalPrice;

    public Order(String itemName, int quantity, double totalPrice){
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
