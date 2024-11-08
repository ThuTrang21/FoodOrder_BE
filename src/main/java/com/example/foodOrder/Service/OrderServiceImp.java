package com.example.foodOrder.Service;

import com.example.foodOrder.model.*;
import com.example.foodOrder.repository.AddressRepository;
import com.example.foodOrder.repository.OrderItemRepository;
import com.example.foodOrder.repository.OrderRepository;
import com.example.foodOrder.repository.UserRepository;
import com.example.foodOrder.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;



    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        Address shipAdd=order.getDeliveryAddress();

        Address saveAdd=addressRepository.save(shipAdd);
        if(!user.getAddresses().contains(saveAdd)){
            user.getAddresses().add(saveAdd);
            userRepository.save(user);
        }
        Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());
        Order createOrder=new Order();
        createOrder.setCustomer(user);
        createOrder.setCreatedAt(new Date());
        createOrder.setOrderStatus("PENDING");
        createOrder.setDeliveryAddress(saveAdd);
        createOrder.setRestaurant(restaurant);

        Cart cart=cartService.findCartByUserId(user.getId());

        int totalITem=0;
        List<OrderItem> orderItems=new ArrayList<>();
        for(CartItem cartItem: cart.getItem()){
            OrderItem orderItem=new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            totalITem+=cartItem.getQuantity();

            OrderItem saveOrderItem=orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }

        Long totalPrice=cartService.calculateCartTotals(cart);
        createOrder.setItems(orderItems);
        createOrder.setTotalPrice(totalPrice);
        createOrder.setTotalItem(totalITem);
        createOrder.setTotalAmount(totalPrice+33+21);

        Order saveOrder=orderRepository.save(createOrder);
        restaurant.getOrders().add(saveOrder);


        return createOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
       Order order=findOrderById(orderId);
       if(orderStatus.equals("OUT_FOR_DELIVERY")
               || orderStatus.equals("DELIVERED")
               || orderStatus.equals("COMPLETED")
               || orderStatus.equals("PENDING")
       ){
           order.setOrderStatus(orderStatus);
           return orderRepository.save(order);
       }
       throw new Exception("Please select a valid order status");

    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order=findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders= orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus!=null){
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> opt=orderRepository.findById(orderId);
        if(opt.isEmpty()){
            throw new Exception("order not found");
        }
        return opt.get();
    }
}
