package com.alibou.security.services;

import com.alibou.security.models.Cart;
import com.alibou.security.models.CartItem;
import com.alibou.security.models.Product;
import com.alibou.security.models.User;

public interface CartService {
    public CartItem addCartItem(User user, Product product, String size,int quantity);
    public Cart findUserCart(User user);
}
