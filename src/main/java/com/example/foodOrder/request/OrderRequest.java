package com.example.foodOrder.request;

import com.example.foodOrder.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
