package com.example.foodOrder.request;

import com.example.foodOrder.model.Address;
import com.example.foodOrder.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {
        private Long id;
        private String name;
        private String description;
        private String cuisineType;
        private Address address;
        private ContactInformation contactInformation;
        private String openingHours;
        private List<String> images;
}
