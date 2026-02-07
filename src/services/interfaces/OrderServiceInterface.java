package services.interfaces;

import models.Order;
import java.util.List;

public interface OrderServiceInterface {
    void makeOrder(List<Order> orders, int adminId) throws Exception;
    List<Order> getOrders() throws Exception;
    List<Order> getOrdersByAdmin(int adminId) throws Exception;
}