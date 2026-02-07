package main;

import models.Admin;
import models.Category;
import models.Item;
import models.Order;
import services.interfaces.AdminServiceInterface;
import services.interfaces.CategoryServiceInterface;
import services.interfaces.ItemServiceInterface;
import services.interfaces.OrderServiceInterface;
import utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyApplication {
    private ItemServiceInterface itemService;
    private OrderServiceInterface orderService;
    private AdminServiceInterface adminService;
    private CategoryServiceInterface categoryService;
    private Scanner scanner = new Scanner(System.in);
    private Admin currentAdmin;

    public MyApplication(ItemServiceInterface itemService,
                         OrderServiceInterface orderService,
                         AdminServiceInterface adminService,
                         CategoryServiceInterface categoryService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.adminService = adminService;
        this.categoryService = categoryService;
    }

    public void start() {
        try {
            currentAdmin = loginMenu();

            if (currentAdmin == null) {
                return;
            }

            System.out.println("Welcome, " + currentAdmin.getUsername() + " (" + currentAdmin.getRole() + ")");

            int choice = -1;
            while (choice != 0) {
                showMenu();

                choice = scanner.nextInt();
                scanner.nextLine();

                handleChoice(choice);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showMenu() {
        if (currentAdmin.isSchoolAdmin()) {
            System.out.println("1 - Make order");
            System.out.println("2 - Show items");
            System.out.println("3 - Show my orders");
            System.out.println("4 - Show categories");
            System.out.println("5 - Create new School Admin");
        } else if (currentAdmin.isFactoryAdmin()) {
            System.out.println("1 - Add new item");
            System.out.println("2 - Edit item");
            System.out.println("3 - Show all items");
            System.out.println("4 - Manage categories");
            System.out.println("5 - View all orders");
            System.out.println("6 - Create new Factory Admin");
        }

        System.out.println("0 - Exit");
        System.out.print("Choice: ");
    }

    private void handleChoice(int choice) throws Exception {
        if (currentAdmin.isSchoolAdmin()) {
            handleSchoolAdmin(choice);
        } else if (currentAdmin.isFactoryAdmin()) {
            handleFactoryAdmin(choice);
        }
    }

    private void handleSchoolAdmin(int choice) throws Exception {
        if (choice == 1) {
            makeOrderMenu();
        } else if (choice == 2) {
            showItemsMenu();
        } else if (choice == 3) {
            showMyOrdersMenu();
        } else if (choice == 4) {
            showCategoriesMenu();
        } else if (choice == 5) {
            createAdminMenu();
        } else if (choice == 0) {
            System.out.println("Goodbye");
        } else {
            System.out.println("Invalid choice");
        }
    }

    private void handleFactoryAdmin(int choice) throws Exception {
        if (choice == 1) {
            addItemMenu();
        } else if (choice == 2) {
            editItemMenu();
        } else if (choice == 3) {
            showItemsMenu();
        } else if (choice == 4) {
            manageCategoriesMenu();
        } else if (choice == 5) {
            showAllOrdersMenu();
        } else if (choice == 6) {
            createAdminMenu();
        } else if (choice == 0) {
            System.out.println("Goodbye");
        } else {
            System.out.println("Invalid choice");
        }
    }

    public Admin loginMenu() throws Exception {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        Admin admin = adminService.login(login, pass);

        if (admin == null) {
            System.out.println("Access denied");
            return null;
        }

        System.out.println("Access granted");
        return admin;
    }

    public void showItemsMenu() throws Exception {
        List<Item> items = itemService.getItems();

        System.out.println("ID Name Price Quantity Category");

        for (Item item : items) {
            String category = item.getCategoryName() != null ? item.getCategoryName() : "N/A";
            System.out.printf("%-5d %-30s $%-9.2f %-10d %-20s%n",
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity(),
                    category);
        }
    }

    public void addItemMenu() throws Exception {
        List<Category> categories = categoryService.getAllCategories();
        System.out.println("Categories:");
        for (Category cat : categories) {
            System.out.println(cat.getId() + " - " + cat.getName());
        }

        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter category ID: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        try {
            Item item = new Item.ItemBuilder()
                    .setName(name)
                    .setPrice(price)
                    .setQuantity(quantity)
                    .setCategoryId(categoryId)
                    .build();

            itemService.addItem(item);
            System.out.println("Item added");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editItemMenu() throws Exception {
        showItemsMenu();

        System.out.print("Enter item ID to edit: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Item item = itemService.findById(id);

        if (item == null) {
            System.out.println("Item not found");
            return;
        }

        System.out.print("New name (Enter to keep): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            item.setName(name);
        }

        System.out.print("New price (0 to keep): ");
        double price = scanner.nextDouble();
        if (price > 0) {
            item.setPrice(price);
        }

        System.out.print("New quantity (-1 to keep): ");
        int quantity = scanner.nextInt();
        if (quantity >= 0) {
            item.setQuantity(quantity);
        }

        try {
            itemService.updateItem(item);
            System.out.println("Item updated");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void makeOrderMenu() throws Exception {
        String answer = "Y";
        List<Order> orders = new ArrayList<>();

        while (answer.equalsIgnoreCase("Y")) {
            showItemsMenu();

            System.out.print("Enter item id: ");
            int id = scanner.nextInt();

            System.out.print("Enter quantity: ");
            int q = scanner.nextInt();
            scanner.nextLine();

            if (!Validator.isValidQuantity(q)) {
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

            System.out.println("Added " + item.getName() + " x" + q);

            System.out.print("Continue? (Y/N): ");
            answer = scanner.next();
            scanner.nextLine();

            if (answer.equalsIgnoreCase("N")) {
                double sum = 0;
                for (Order order : orders) {
                    sum += order.getTotalPrice();
                }

                if (sum > 5000) {
                    double discount = sum * 0.05;
                    sum -= discount;
                    System.out.println("Discount: " + discount);
                }

                System.out.println("Total: " + sum);
                System.out.print("Confirm order? (Y/N): ");
                String confirm = scanner.next();
                scanner.nextLine();

                if (confirm.equalsIgnoreCase("Y")) {
                    orderService.makeOrder(orders, currentAdmin.getId());
                    System.out.println("Order confirmed");
                } else {
                    System.out.println("Order canceled");
                }
            }
        }
    }

    public void showMyOrdersMenu() throws Exception {
        List<Order> orders = orderService.getOrdersByAdmin(currentAdmin.getId());

        System.out.println("My orders");

        for (Order order : orders) {
            System.out.println(
                    order.getId() + " " +
                            order.getItemName() + " " +
                            order.getQuantity() + " " +
                            order.getTotalPrice()
            );
        }
    }

    public void showAllOrdersMenu() throws Exception {
        List<Order> orders = orderService.getOrders();

        System.out.println("All orders");

        for (Order order : orders) {
            System.out.println(
                    order.getId() + " " +
                            order.getItemName() + " " +
                            order.getQuantity() + " " +
                            order.getTotalPrice()
            );
        }
    }

    public void showCategoriesMenu() throws Exception {
        List<Category> categories = categoryService.getAllCategories();

        for (Category cat : categories) {
            System.out.println(cat.getId() + " - " + cat.getName() + ": " + cat.getDescription());
        }
    }

    public void manageCategoriesMenu() throws Exception {
        System.out.println("1 - Add category");
        System.out.println("2 - View categories");
        System.out.println("3 - Edit category");
        System.out.print("Choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            addCategoryMenu();
        } else if (choice == 2) {
            showCategoriesMenu();
        } else if (choice == 3) {
            editCategoryMenu();
        }
    }

    public void addCategoryMenu() throws Exception {
        System.out.print("Enter category name: ");
        String name = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        categoryService.addCategory(name, description);
        System.out.println("Category added");
    }

    public void editCategoryMenu() throws Exception {
        showCategoriesMenu();

        System.out.print("Enter category ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Category category = categoryService.getCategoryById(id);

        if (category == null) {
            System.out.println("Category not found");
            return;
        }

        System.out.print("New name: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            category.setName(name);
        }

        System.out.print("New description: ");
        String description = scanner.nextLine();
        if (!description.isEmpty()) {
            category.setDescription(description);
        }

        categoryService.updateCategory(category);
        System.out.println("Category updated");
    }

    public void createAdminMenu() throws Exception {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        adminService.createAdmin(username, password, currentAdmin.getRole(), currentAdmin);
        System.out.println("Admin created");
    }
}
