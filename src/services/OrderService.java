package services;

import models.Item;
import models.Order;
import repositories.ItemRepository;
import repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    private final OrderRepository repo;
    private final ItemRepository itemRepo;

    public OrderService(OrderRepository repo, ItemRepository itemRepo){
        this.repo = repo;
        this.itemRepo = itemRepo;
    }

    public void makeOrder(List<Order> orders) throws Exception {
        repo.saveOrders(orders);

        List<Item> items = new ArrayList<>();
        for (Order order : orders) {
            Item item = itemRepo.getItemByName(order.getItemName());

            item.decreaseQuantity(order.getQuantity());
            items.add(item);
        }

        itemRepo.saveItems(items);
    }

    public List<Order> getOrders() throws Exception {
        return repo.getOrders();
    }
}