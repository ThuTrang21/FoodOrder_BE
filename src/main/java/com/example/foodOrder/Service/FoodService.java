package com.example.foodOrder.Service;

import com.example.foodOrder.model.Category;
import com.example.foodOrder.model.Food;
import com.example.foodOrder.model.Restaurant;
import com.example.foodOrder.request.CreateFoodRequest;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);
    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegitarain,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory);
    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId) throws Exception;
    public Food updateAvailabilityStatus(Long foodId) throws Exception;

}
