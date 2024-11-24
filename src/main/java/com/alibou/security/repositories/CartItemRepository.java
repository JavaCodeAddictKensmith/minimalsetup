package com.alibou.security.repositories;

import com.alibou.security.models.Cart;
import com.alibou.security.models.CartItem;
import com.alibou.security.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProductAndSize(Cart cart, Product product,String size);

}
