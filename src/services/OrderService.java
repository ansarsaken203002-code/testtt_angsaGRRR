package services;

import modeli.Item;
import modeli.Order;

import java.util.ArrayList;
import java.util.Scanner;

public class OrderService {

    private ArrayList<Order> orders = new ArrayList<>();
    private Scanner sc;

    public OrderService(Scanner sc){
        this.sc = sc;
    }

    public void makeOrder(ItemService itemService){
        int startIndex = orders.size();
        String answer = "Y";

        while(answer.equalsIgnoreCase("Y")){


            itemService.showItems();

            System.out.print("Enter item id: ");
            int id = sc.nextInt();

            System.out.print("Enter quantity: ");
            int q = sc.nextInt();

            Item item = itemService.findById(id);
            if(item == null || q > item.quantity){
                System.out.println("Error: item not found or not enough quantity");
                continue;
            }

            item.quantity -= q;
            double total = item.price * q;
            orders.add(new Order(item.name, q, total));

            System.out.println("Added " + item.name + " x" + q + " Price: " + total);
            System.out.print("Continue? (Y/N): ");
            answer = sc.next();

            if(answer.equalsIgnoreCase("N")){
                double sum = 0;
                for(int i = startIndex; i < orders.size(); i++){
                    sum += orders.get(i).totalPrice;
                }

                System.out.println("Total order sum: " + sum);
                System.out.print("Confirm order? (Y/N): ");
                String confirm = sc.next();

                if(confirm.equalsIgnoreCase("N")){
                    while(orders.size() > startIndex){
                        Order o = orders.remove(orders.size() - 1);
                        Item it = itemService.findByName(o.itemName);
                        if(it != null){
                            it.quantity += o.quantity;
                        }
                    }
                    System.out.println("Order canceled");
                } else {
                    System.out.println("Order confirmed");
                }
            }

        }
    }

    public void showOrders(){
        if(orders.isEmpty()){
            System.out.println("No orders yet.");
            return;
        }
        for(Order o : orders){
            System.out.println(o.itemName + " x" + o.quantity + " = " + o.totalPrice);
        }
    }

    public void cancelLast(ItemService itemService){
        if(orders.isEmpty()){
            System.out.println("No orders to cancel.");
            return;
        }
        Order last = orders.remove(orders.size() - 1);
        Item item = itemService.findByName(last.itemName);
        if(item != null){
            item.quantity += last.quantity;
        }
        System.out.println("Last order canceled: " + last.itemName);
    }

    public ArrayList<Order> getOrders(){
        return orders;
    }
}
