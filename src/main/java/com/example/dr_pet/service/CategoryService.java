package com.example.dr_pet.service;

import com.example.dr_pet.DTO.Response.CategoryResponse;
import com.example.dr_pet.model.Category;
import com.example.dr_pet.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    private CategoryResponse maptoCategoryResponse(Category category) {
        CategoryResponse dto = new CategoryResponse();
        dto.setCategoryID(category.getCategoryID());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }


    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream().map(this::maptoCategoryResponse).collect(Collectors.toList());
    }









}
