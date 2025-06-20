package com.example.dr_pet.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveFromCartResponse {
    private Long productID;
    private Integer removedQuantity;
    private String message;
}