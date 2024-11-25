package com.alibou.security.services.impl;

import com.alibou.security.models.CartItem;
import com.alibou.security.models.User;
import com.alibou.security.repositories.CartItemRepository;
import com.alibou.security.services.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private  final CartItemRepository cartItemRepository;
    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception {
        CartItem item = findCartItemById(id);
        User cartItemUser = item.getCart().getUser();
        if(cartItemUser.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            return  cartItemRepository.save(item);
        }
        throw  new Exception("You can't update update this cartitem");
    }



    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
        CartItem item = findCartItemById(cartItemId);
        User cartItemUser= item.getCart().getUser();
        if(cartItemUser.getId().equals(userId)){
            cartItemRepository.delete(item);
        }else  throw  new Exception("You can't delete this item");



    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {
        return cartItemRepository.findById(id).orElseThrow(()->new Exception("cart item not found with id "+id));
    }
}
