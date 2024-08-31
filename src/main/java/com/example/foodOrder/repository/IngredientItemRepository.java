package com.example.foodOrder.repository;

import com.example.foodOrder.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemRepository extends JpaRepository<IngredientsItem,Long> {
    List<IngredientsItem> findByRestaurantId(Long id);
}
