package com.alibou.security.repositories;

import com.alibou.security.models.Product;
import com.alibou.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
