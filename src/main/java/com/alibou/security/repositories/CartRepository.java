package com.alibou.security.repositories;

import com.alibou.security.models.Cart;
import com.alibou.security.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long id);
}
