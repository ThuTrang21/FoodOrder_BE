package com.example.foodOrder.model;

import jakarta.persistence.Embeddable;
import lombok.*;


@Data
@Embeddable
public class ContactInformation {
    private String email;
    private String mobile;
    private String facebook;
    private String instagram;
}
