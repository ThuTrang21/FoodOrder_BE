package com.example.foodOrder.controller;

import com.example.foodOrder.Service.IngredientService;
import com.example.foodOrder.model.IngredientsCategory;
import com.example.foodOrder.model.IngredientsItem;
import com.example.foodOrder.request.IngredientCategoryRequest;
import com.example.foodOrder.request.IngredientItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/category")
    public ResponseEntity<IngredientsCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req) throws Exception {
        IngredientsCategory category=ingredientService.createIngredientsCategory(req.getName(),req.getRestaurantId());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest req) throws Exception {
        IngredientsItem item=ingredientService.createIngredientsItem(req.getRestaurantId(),req.getName(), req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateStoke(@PathVariable Long id) throws Exception {
        IngredientsItem item=ingredientService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }


    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(@PathVariable Long id) throws Exception {
       List<IngredientsItem>  items=ingredientService.findRestaurantIngredients(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientsCategory>> getRestaurantIngredientCategory(
            @PathVariable Long id) throws Exception {
        List<IngredientsCategory>  items=ingredientService.findIngredientsCategoryByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
