package main;

import java.util.Scanner;
import java.util.ArrayList;

import services.AdminService;
import services.ItemService;
import r.e.p.o.ItemRepository;
import r.e.p.o.OrderRepository;
import modeli.Item;
import modeli.Order;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        AdminService admin = new AdminService();
        try {

            if(!admin.login(login, pass)){
                System.out.println("Access denied");
                return;
            }
            System.out.println("Access granted");

            ItemRepository itemRepo = new ItemRepository();
            ArrayList<Item> items = itemRepo.loadItems();

            ItemService itemService = new ItemService(items);

            OrderRepository orderRepo = new OrderRepository();
            ArrayList<Order> orders = new ArrayList<>();

            int choice = -1;
            while(choice != 0){
                System.out.println("\n1 - Make order");
                System.out.println("2 - Show items");
                System.out.println("3 - Show orders");
                System.out.println("4 - Cancel last order");
                System.out.println("0 - Exit");
                choice = sc.nextInt();

                if(choice == 1){
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
                    }
                }

                if(choice == 2){
                    System.out.println("\nItems in stock:");
                    itemService.showItems();
                }

                if(choice == 3){
                    System.out.println("\nOrder history:");
                    if(orders.isEmpty()){
                        System.out.println("No orders yet.");
                    } else {
                        for(Order o : orders){
                            System.out.println(o.itemName + " x" + o.quantity + " = " + o.totalPrice);
                        }
                    }
                }

                if(choice == 4){
                    if(orders.isEmpty()){
                        System.out.println("No orders to cancel.");
                    } else {
                        Order last = orders.remove(orders.size() - 1);
                        Item item = itemService.findByName(last.itemName);
                        if(item != null){
                            item.quantity += last.quantity;
                        }
                        System.out.println("Last order canceled: " + last.itemName + " x" + last.quantity);
                    }
                }
            }

            itemRepo.saveItems(itemService.getItems());
            orderRepo.saveOrders(orders);

        } catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
