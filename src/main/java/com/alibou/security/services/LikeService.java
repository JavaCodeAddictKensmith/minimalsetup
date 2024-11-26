package com.alibou.security.services;

public interface LikeService {
    String likeProduct(Long productId);

    String unLikeProduct(Long productId);
}
