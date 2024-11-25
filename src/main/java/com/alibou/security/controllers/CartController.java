package com.alibou.security.controllers;

import com.alibou.security.config.CustomUserDetails;
import com.alibou.security.config.JwtService;
import com.alibou.security.dto.responses.CartResponseDto;
import com.alibou.security.exceptions.CustomException;
import com.alibou.security.exceptions.UserNotFoundException;
import com.alibou.security.models.Cart;
import com.alibou.security.models.CartItem;
import com.alibou.security.models.User;
import com.alibou.security.repositories.UserRepository;
import com.alibou.security.services.CartItemService;
import com.alibou.security.services.CartService;
import com.alibou.security.services.ProductService;
import com.alibou.security.utility.LoggedInUserUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
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
