package main;

import connectim_bazuuu.DBConnection;
import repositories.*;
import repositories.interfaces.*;
import services.*;
import services.interfaces.*;

public class Main {

    public static void main(String[] args) {
        try {
            DBConnection conn = DBConnection.getInstance();

            ItemRepositoryInterface itemRepo = new ItemRepository(conn);
            OrderRepositoryInterface orderRepo = new OrderRepository(conn);
            AdminRepositoryInterface adminRepo = new AdminRepository(conn);
            CategoryRepositoryInterface categoryRepo = new CategoryRepository(conn);

            ItemServiceInterface itemService = new ItemService(itemRepo);
            OrderServiceInterface orderService = new OrderService(orderRepo, itemRepo);
            AdminServiceInterface adminService = new AdminService(adminRepo);
            CategoryServiceInterface categoryService = new CategoryService(categoryRepo);

            MyApplication app = new MyApplication(
                    itemService,
                    orderService,
                    adminService,
                    categoryService
            );

            app.start();

            conn.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}