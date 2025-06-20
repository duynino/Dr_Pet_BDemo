package com.example.dr_pet.controller;

import com.example.dr_pet.DTO.Request.AddToCartRequest;
import com.example.dr_pet.DTO.Response.AddToCartResponse;
import com.example.dr_pet.model.UserPrincipal;
import com.example.dr_pet.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.dr_pet.DTO.Request.RemoveFromCartRequest;
import com.example.dr_pet.DTO.Response.RemoveFromCartResponse;

import com.example.dr_pet.DTO.Request.OrderRequest;
import com.example.dr_pet.DTO.Response.OrderResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;



@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                       @Valid @RequestBody AddToCartRequest request,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity.badRequest().body(errMsg);
        }
        try {
            AddToCartResponse response = cartService.addToCart(userPrincipal.getUsername(), request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @Valid @RequestBody RemoveFromCartRequest request,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity.badRequest().body(errMsg);
        }
        try {
            RemoveFromCartResponse response = cartService.removeFromCart(userPrincipal.getUsername(), request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                          @RequestBody OrderRequest request) {
        try {
            OrderResponse response = cartService.checkoutCart(userPrincipal.getUsername(), request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
