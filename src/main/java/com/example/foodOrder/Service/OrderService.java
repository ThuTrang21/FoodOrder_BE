package com.example.foodOrder.Service;

import com.example.foodOrder.model.Order;
import com.example.foodOrder.model.User;
import com.example.foodOrder.request.OrderRequest;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest order, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;
    public List<Order> getUserOrder(Long userId) throws Exception;
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception;
    public Order findOrderById(Long orderId)throws Exception;
}
