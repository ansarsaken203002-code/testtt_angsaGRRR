package repositories.interfaces;

import models.Order;
import java.util.List;

public interface OrderRepositoryInterface {
    void saveOrders(List<Order> orders, int adminId) throws Exception;
    List<Order> getOrders() throws Exception;
    List<Order> getOrdersByAdmin(int adminId) throws Exception;
}