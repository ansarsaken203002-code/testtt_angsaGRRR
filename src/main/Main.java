package main;

import java.util.Scanner;
import java.util.ArrayList;

import connectim_bazuuu.DBConnection;
import services.AdminService;
import services.ItemService;
import services.OrderService;
import repositories.ItemRepository;
import repositories.OrderRepository;
import models.Item;

public class Main {

    public static void main(String[] args) {
        try {
            DBConnection conn = new DBConnection();
            ItemRepository itemRepo = new ItemRepository(conn);
            ItemService itemService = new ItemService(itemRepo);

            OrderRepository orderRepo = new OrderRepository(conn);
            OrderService orderService = new OrderService(orderRepo, itemRepo);

            AdminService adminService = new AdminService(conn);

            MyApplication app = new MyApplication(itemService, orderService, adminService);

            app.start();

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
