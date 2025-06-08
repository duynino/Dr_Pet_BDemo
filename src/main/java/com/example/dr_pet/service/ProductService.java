package com.example.dr_pet.service;


import com.example.dr_pet.DTO.Response.ProductResponse;
import com.example.dr_pet.model.Category;
import com.example.dr_pet.model.Product;
import com.example.dr_pet.repo.CategoryRepo;
import com.example.dr_pet.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    //list all product to response
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }


    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setProductID(product.getProductID());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setImageURL(product.getImageURl());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
        return dto;
    }

    public ProductResponse getProductById(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return mapToProductResponse(product);
    }

    public List<ProductResponse> getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        List<Product> products = productRepo.findByCategory(category);
        return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }


}
