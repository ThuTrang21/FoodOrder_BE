package com.example.foodOrder.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Embeddable
public class RestaurantDTO {
    private String title;

    @Column(length = 1000)
    private List<String> images;

    private String description;

    private Long id;
}
