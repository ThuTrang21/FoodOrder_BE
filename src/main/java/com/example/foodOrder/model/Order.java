package com.example.foodOrder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders") // Đặt tên tùy chỉnh cho bảng
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User customer;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    private Long totalAmount;

    private String orderStatus;

    private Date createdAt;
    @ManyToOne
    private Address deliveryAddress;
    @OneToMany
    private List<OrderItem> items;

    //private Payment payment;
    private int totalItem;

    private Long totalPrice;
}
