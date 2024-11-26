package com.alibou.security.controllers;


import com.alibou.security.dto.responses.CartResponseDto;

import com.alibou.security.services.CartService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addProductToCart/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.addProductToCart(productId));
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/removeProductFromCart/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(productId));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viewCart")
    public ResponseEntity<CartResponseDto> viewCart() {
        return ResponseEntity.ok(cartService.viewCart());
    }





}