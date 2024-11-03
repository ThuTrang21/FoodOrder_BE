package com.example.foodOrder.controller;

import com.example.foodOrder.Service.FoodService;
import com.example.foodOrder.Service.RestaurantService;
import com.example.foodOrder.Service.UserService;
import com.example.foodOrder.model.Food;
import com.example.foodOrder.model.Restaurant;
import com.example.foodOrder.model.User;
import com.example.foodOrder.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@PathVariable String name,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        List<Food> foods=foodService.searchFood(name);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable (required = false) boolean vagetarian,
                                                        @PathVariable (required = false) boolean seasonal,
                                                        @PathVariable (required = false) boolean nonveq,
                                                        @RequestParam(required = false) String food_category,
                                                        @PathVariable Long restaurantId,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        List<Food> foods=foodService.getRestaurantsFood(restaurantId,vagetarian,nonveq,seasonal,food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

}
