package com.example.foodOrder.repository;

import com.example.foodOrder.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    public List<Event> findByRestaurantId(Long restaurantId);
}
