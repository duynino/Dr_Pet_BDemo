package com.example.dr_pet.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartResponse {
    private Long cartId;
    private Long productId;
    private Integer quantity;
    private Integer totalItemsInCart;
    private String message;
}