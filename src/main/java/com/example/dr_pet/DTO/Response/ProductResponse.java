package com.example.dr_pet.DTO.Response;


import com.example.dr_pet.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long productID;
    private String name;
    private String description;
    private String imageURL;
    private Float price;
    private Integer quantity;
    private Category category;
}
