package com.example.foodOrder.Service;


import com.example.foodOrder.model.IngredientsCategory;
import com.example.foodOrder.model.IngredientsItem;

import java.util.List;

public interface IngredientService {
    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception;

    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception;

    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception;

    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId);

    public IngredientsItem updateStock(Long id) throws Exception;


}
