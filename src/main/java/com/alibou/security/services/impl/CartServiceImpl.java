package com.alibou.security.services.impl;

import com.alibou.security.models.Cart;
import com.alibou.security.models.CartItem;
import com.alibou.security.models.Product;
import com.alibou.security.models.User;
import com.alibou.security.repositories.CartItemRepository;
import com.alibou.security.repositories.CartRepository;
import com.alibou.security.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl  implements CartService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int qauntity) {
        Cart cart= findUserCart(user);
        CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(cart,product,size);
        if(isPresent==null){
            CartItem cartItem= new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(qauntity);
            cartItem.setUserId(user.getId());
            cartItem.setSize(size);
            double totalPrice = qauntity* product.getPrice();
            cartItem.setPrice(totalPrice);
            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);
            return cartItemRepository.save(cartItem);

        }

        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());
        int totalPrice =0;
        int totalDiscountPrice = 0;
        int totalItem =0;
        for(CartItem cartItem: cart.getCartItems()){
            totalPrice += cartItem.getPrice();
         totalItem+= cartItem.getQuantity();

        }
        cart.setTotalSellingPrice(totalPrice);
        cart.setTotalItem(totalItem);


        return cart;
    }
}
