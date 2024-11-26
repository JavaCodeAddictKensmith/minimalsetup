package com.alibou.security.repositories;

import com.alibou.security.models.Cart;
import com.alibou.security.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
