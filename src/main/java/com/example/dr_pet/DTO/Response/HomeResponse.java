package com.example.dr_pet.DTO.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponse {
    List<CategoryResponse> categories;
    List<ProductResponse> products;
}
