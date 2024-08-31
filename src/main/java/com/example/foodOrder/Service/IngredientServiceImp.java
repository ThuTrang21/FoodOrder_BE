package com.example.foodOrder.Service;

import com.example.foodOrder.model.IngredientsCategory;
import com.example.foodOrder.model.IngredientsItem;
import com.example.foodOrder.model.Restaurant;
import com.example.foodOrder.repository.IngredientCategoryRepository;
import com.example.foodOrder.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientService{
    @Autowired
    private IngredientItemRepository ingredientItemRepository;
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;


    @Override
    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);

        IngredientsCategory ingredientsCategory=new IngredientsCategory();
        ingredientsCategory.setRestaurant(restaurant);
        ingredientsCategory.setName(name);

        return ingredientCategoryRepository.save(ingredientsCategory);
    }

    @Override
    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception {
        Optional<IngredientsCategory> opt=ingredientCategoryRepository.findById(id);
        if(opt.isEmpty())
            throw new Exception("Ingredient Category not found");
        return opt.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);

        IngredientsCategory category=findIngredientsCategoryById(categoryId);
        IngredientsItem item=new IngredientsItem();
        item.setRestaurant(restaurant);
        item.setName(ingredientName);
        item.setCategory(category);

        IngredientsItem ingredientsItem=ingredientItemRepository.save(item);
        category.getIngredients().add(ingredientsItem);


        return ingredientsItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {

        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> opt=ingredientItemRepository.findById(id);
        if(opt.isEmpty())
            throw new Exception("Ingredient Item not found");
        IngredientsItem ingredientsItem=opt.get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        return ingredientItemRepository.save(ingredientsItem);
    }
}
