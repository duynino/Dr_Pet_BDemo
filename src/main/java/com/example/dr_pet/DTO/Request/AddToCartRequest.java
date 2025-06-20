package com.example.dr_pet.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequest {
    @NotNull(message = "productID must not be null")
    private Long productID;

    @NotNull(message = "quantity must not be null")
    private Integer quantity;
}
