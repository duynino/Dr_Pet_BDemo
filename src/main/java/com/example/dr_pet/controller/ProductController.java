package com.example.dr_pet.controller;


import com.example.dr_pet.DTO.Request.ProductRequest;
import com.example.dr_pet.DTO.Response.ProductResponse;
import com.example.dr_pet.model.Product;
import com.example.dr_pet.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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


    //product detail
    @GetMapping("detail/{id}")
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


    //list product by category
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


    //update product
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(errMsg);
        }
        try{
            ProductResponse updated = productService.updateProduct(id, request);
            return ResponseEntity.ok("Update successfully");
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    //delete product
    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    //add product
    @PostMapping("add")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
        }
        try {
            ProductResponse resp = productService.addProduct(request);
            return ResponseEntity.ok("Add successfully");
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


}
