package main;

import models.Item;
import models.Order;
import services.AdminService;
import services.ItemService;
import services.OrderService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyApplication {
    private final ItemService itemService;
    private final OrderService orderService;
    private final AdminService adminService;
    private final Scanner scannirui = new Scanner(System.in);


    public MyApplication(ItemService itemService, OrderService orderService, AdminService admin) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.adminService = admin;
    }

    public void start() {
        try {
            boolean success = loginMenu();

            if (!success) {
                return;
            }

            int choice = -1;
            while (choice != 0) {
                System.out.println("\n1 - Make order");
                System.out.println("2 - Show items");
                System.out.println("3 - Show orders");
                System.out.println("0 - Exit");

                choice = scannirui.nextInt();

                if (choice == 1) makeOrderMenu();
                if (choice == 2) showItemsMenu();
                if (choice == 3) showOrdersMenu();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showItemsMenu() throws Exception {
        List<Item> items = itemService.getItems();
        items.forEach(i ->
                System.out.println(
                        i.getId() + " " +
                                i.getName() + " " +
                                i.getPrice() + " " +
                                i.getQuantity()
                )
        );
    }

    public boolean loginMenu() throws Exception {
        System.out.print("Login: ");
        String login = scannirui.nextLine();
        System.out.print("Password: ");
        String pass = scannirui.nextLine();

        if(!adminService.login(login, pass)){
            System.out.println("Access denied");
            return false;
        }

        System.out.println("Access granted");

        return true;
    }

    public void makeOrderMenu() throws Exception {
        String answer = "Y";

        List<Order> orders = new ArrayList<>();
        while(answer.equalsIgnoreCase("Y")){

            showItemsMenu();

            System.out.print("Enter item id: ");
            int id = scannirui.nextInt();

            System.out.print("Enter quantity: ");
            int q = scannirui.nextInt();

            if (q <= 0) {
                System.out.println("Quantity must be positive");
                continue;
            }


            Item item = itemService.findById(id);
            if (item == null) {
                System.out.println("Item not found");
                continue;
            }

            if (q > item.getQuantity()) {
                System.out.println("Not enough quantity");
                continue;
            }

            double total = item.getPrice() * q;
            orders.add(new Order(item.getName(), q, total));

            System.out.println("Added " + item.getName() + " x" + q + " Price: " + total);

            System.out.print("Continue? (Y/N): ");
            answer = scannirui.next();

            if(answer.equalsIgnoreCase("N")){
                double sum = 0;
                for(Order order : orders){
                    sum += order.getTotalPrice();
                }

                if(sum > 5000){
                    double discount = sum * 0.05;
                    sum -= discount;
                    System.out.println("Discount applied: " + discount);
                }

                System.out.println("Total order sum: " + sum);
                System.out.print("Confirm order? (Y/N): ");
                String confirm = scannirui.next();

                if(confirm.equalsIgnoreCase("N")){
                    System.out.println("Order canceled");
                } else {
                    orderService.makeOrder(orders);
                    System.out.println("Order confirmed");
                }
            }
        }
    }

    public void showOrdersMenu() throws Exception {
        List<Order> orders = orderService.getOrders();
        orders.forEach(i ->
                System.out.println(
                        i.getId() + " " +
                                i.getItemName() + " " +
                                i.getQuantity() + " " +
                                i.getTotalPrice()
                )
        );
    }
}
