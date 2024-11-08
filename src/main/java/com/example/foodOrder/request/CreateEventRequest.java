package com.example.foodOrder.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventRequest {
    private String name;
    private String location;
    private LocalDateTime startedAt;
    private LocalDateTime endsAt;
    private String image;
    private Long restaurantId;
}
