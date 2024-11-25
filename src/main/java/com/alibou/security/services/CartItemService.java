package com.alibou.security.services;

import com.alibou.security.models.Cart;
import com.alibou.security.models.CartItem;


    public interface CartItemService {
        CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception;
        void removeCartItem(Long userId,Long cartItemId) throws Exception;
        CartItem findCartItemById(Long id) throws Exception;
    }

