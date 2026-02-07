package services;

import models.Item;
import models.Order;
import repositories.interfaces.ItemRepositoryInterface;
import repositories.interfaces.OrderRepositoryInterface;
import services.interfaces.OrderServiceInterface;
import java.util.ArrayList;
import java.util.List;

public class OrderService implements OrderServiceInterface {
    private OrderRepositoryInterface repo;
    private ItemRepositoryInterface itemRepo;

    public OrderService(OrderRepositoryInterface repo, ItemRepositoryInterface itemRepo) {
        this.repo = repo;
        this.itemRepo = itemRepo;
    }

    public void makeOrder(List<Order> orders, int adminId) throws Exception {
        repo.saveOrders(orders, adminId);

        List<Item> items = new ArrayList<>();
        for (Order order : orders) {
            Item item = itemRepo.getItemByName(order.getItemName());
            if (item != null) {
                item.decreaseQuantity(order.getQuantity());
                items.add(item);
            }
        }

        itemRepo.saveItems(items);
    }

    public List<Order> getOrders() throws Exception {
        return repo.getOrders();
    }

    public List<Order> getOrdersByAdmin(int adminId) throws Exception {
        return repo.getOrdersByAdmin(adminId);
    }
}