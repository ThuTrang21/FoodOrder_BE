package com.example.foodOrder.Service;

import com.example.foodOrder.model.Category;
import com.example.foodOrder.model.Food;
import com.example.foodOrder.model.Restaurant;
import com.example.foodOrder.repository.FoodRepository;
import com.example.foodOrder.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService{
    @Autowired
    private FoodRepository foodRepository;


    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food=new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setName(req.getName());
        food.setImages(req.getImages());
        food.setPrice(req.getPrice());
        food.setDescription(req.getDescription());
        food.setAvailable(req.isAvailable());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());
    food.setCreationDate(new Date());

        Food saveFood=foodRepository.save(food);
        restaurant.getFoods().add(saveFood);
        return saveFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
//        foodRepository.delete(food);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegitarain,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory) {
        List<Food> foods=foodRepository.findByRestaurantId(restaurantId);

        if(isVegitarain){
            foods=filterByVegetarian(foods,isVegitarain);
        }
        if(isNonveg){
            foods=filterByNonveg(foods,isNonveg);
        }
        if(isSeasonal)
            foods=filterBySeasonal(foods,isSeasonal);
        if(foodCategory!=null && !foodCategory.equals(("")))
            foods=filterByCategory(foods,foodCategory);
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);

            }
            return false;
        }).collect(Collectors.toList());

    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());

    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isSeasonal()==false).collect(Collectors.toList());

    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegitarain) {
        return foods.stream().filter(food -> food.isVegetarian()==isVegitarain).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {

        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood=foodRepository.findById(foodId);
        if(optionalFood.isEmpty()){
            throw new Exception("Not found");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);

    }
}
