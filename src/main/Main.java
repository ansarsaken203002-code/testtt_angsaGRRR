package main;

import java.util.Scanner;
import java.util.ArrayList;

import services.AdminService;
import services.ItemService;
import services.OrderService;
import r.e.p.o.ItemRepository;
import r.e.p.o.OrderRepository;
import modeli.Item;

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

            OrderService orderService = new OrderService(sc);
            OrderRepository orderRepo = new OrderRepository();

            int choice = -1;
            while(choice != 0){
                System.out.println("\n1 - Make order");
                System.out.println("2 - Show items");
                System.out.println("3 - Show orders");
                System.out.println("4 - Cancel last order");
                System.out.println("0 - Exit");

                choice = sc.nextInt();

                if(choice == 1) orderService.makeOrder(itemService);
                if(choice == 2) itemService.showItems();
                if(choice == 3) orderService.showOrders();
                if(choice == 4) orderService.cancelLast(itemService);
            }

            itemRepo.saveItems(itemService.getItems());
            orderRepo.saveOrders(orderService.getOrders());

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
