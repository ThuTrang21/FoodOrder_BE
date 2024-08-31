package com.example.foodOrder.request;

import com.example.foodOrder.model.Category;
import com.example.foodOrder.model.IngredientsItem;
import com.example.foodOrder.model.Restaurant;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    private Category foodCategory;
    private List<String> images;
//    private boolean available;
    private Long restaurantId;
    private boolean isVegetarian;
    private boolean isSeasonal;
    private List<IngredientsItem> ingredients= new ArrayList<>();
//    private Date creationDate;
}
