package com.alibou.security.controllers;

import com.alibou.security.config.JwtService;
import com.alibou.security.services.CartItemService;
import com.alibou.security.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private  final CartItemService cartItemService;
    private final JwtService jwtService;
//    String user = jwtService.getLoggedInUserFromToken()


}
