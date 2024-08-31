package com.example.foodOrder.controller;

import com.example.foodOrder.Service.CategoryService;
import com.example.foodOrder.Service.UserService;
import com.example.foodOrder.model.Category;
import com.example.foodOrder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @PostMapping("admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization") String jwt
                                                   ) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        Category category1=categoryService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(category1, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        List<Category> categories=categoryService.findCategoryRestaurantId(user.getId());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


}
