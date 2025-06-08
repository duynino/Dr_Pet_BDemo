package com.example.dr_pet.controller;


import com.example.dr_pet.DTO.Response.ProductResponse;
import com.example.dr_pet.model.Product;
import com.example.dr_pet.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;


    //list all product
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<ProductResponse> list = productService.getAllProducts();
        if (list.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Không có sản phẩm nào trong hệ thống");
        }
        return ResponseEntity.ok(list);
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        try {
            ProductResponse product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy sản phẩm với id = " + id);
        }
    }


    @GetMapping("category/{id}")
    public ResponseEntity<?> getProductByCategory(@PathVariable("id") Long id) {
        List<ProductResponse> list = productService.getProductsByCategoryId(id);
        if (list.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Không có sản phẩm thuộc category id = " + id);
        }
        return ResponseEntity.ok(list);
    }



}
