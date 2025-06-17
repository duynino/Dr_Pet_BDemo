package com.example.dr_pet.controller;


import com.example.dr_pet.DTO.Response.CategoryResponse;
import com.example.dr_pet.DTO.Response.HomeResponse;
import com.example.dr_pet.DTO.Response.ProductResponse;
import com.example.dr_pet.model.Account;
import com.example.dr_pet.service.CategoryService;
import com.example.dr_pet.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("")
@RestController
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // product and category list in home page
    @GetMapping
    public ResponseEntity<?> homePage(){
        List<ProductResponse> productResponseList = productService.getAllProducts();
        List<CategoryResponse> categoryResponseList = categoryService.getAllCategory();
        HomeResponse homeResponse = new HomeResponse(categoryResponseList, productResponseList);
        return ResponseEntity.ok(homeResponse);
    }


}
