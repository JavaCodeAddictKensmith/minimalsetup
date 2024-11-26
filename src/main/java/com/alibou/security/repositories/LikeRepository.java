package com.alibou.security.repositories;

import com.alibou.security.models.Cart;
import com.alibou.security.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndProductId(Long userId, Long productId);
}


//public interface LikeRepository extends JpaRepository<Like, Long> {
//}
