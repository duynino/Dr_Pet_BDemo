package com.example.dr_pet.DTO.Request;

import com.example.dr_pet.model.Category;
import com.example.dr_pet.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private String imageURL;
    private Float price;
    private Integer quantity;
    private Category category;
    private Supplier supplier;
}
