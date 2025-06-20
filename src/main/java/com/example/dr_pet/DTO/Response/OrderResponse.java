package com.example.dr_pet.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long orderID;
    private Float totalPrice;
    private Float discountPrice;
    private String message;
}
