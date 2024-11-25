package com.alibou.security.services;

import com.alibou.security.dto.responses.CartResponseDto;
import com.alibou.security.models.Cart;
import com.alibou.security.models.CartItem;
import com.alibou.security.models.Product;
import com.alibou.security.models.User;
import org.springframework.transaction.annotation.Transactional;

public interface CartService {
    @Transactional
    String addProductToCart(Long productId);

    String removeProductFromCart(Long productId);

    CartResponseDto viewCart();
}
