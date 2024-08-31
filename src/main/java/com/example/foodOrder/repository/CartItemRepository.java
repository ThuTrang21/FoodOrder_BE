package com.example.foodOrder.repository;

import com.example.foodOrder.model.Cart;
import com.example.foodOrder.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

}
