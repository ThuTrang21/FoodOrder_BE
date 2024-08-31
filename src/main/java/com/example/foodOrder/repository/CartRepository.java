package com.example.foodOrder.repository;

import com.example.foodOrder.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    public Cart findByCustomerId(@Param("userId") Long userId);

}
