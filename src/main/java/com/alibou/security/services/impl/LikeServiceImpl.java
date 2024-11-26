package com.alibou.security.services.impl;

import com.alibou.security.exceptions.CustomException;
import com.alibou.security.exceptions.UserNotFoundException;
import com.alibou.security.models.CartItem;
import com.alibou.security.models.Like;
import com.alibou.security.models.Product;
import com.alibou.security.models.User;
import com.alibou.security.repositories.LikeRepository;
import com.alibou.security.repositories.ProductRepository;
import com.alibou.security.repositories.UserRepository;
import com.alibou.security.services.LikeService;
import com.alibou.security.utility.LoggedInUserUtil;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final LoggedInUserUtil loggedInUserUtil;

    public LikeServiceImpl(ProductRepository productRepository, UserRepository userRepository,
                           LikeRepository likeRepository, LoggedInUserUtil loggedInUserUtil) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.loggedInUserUtil = loggedInUserUtil;
    }

    @Override
    public String likeProduct(Long productId) {
        Long userId = loggedInUserUtil.getLoggedInUser().getId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Product not found"));
        User user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Check if the like already exists
        if (likeRepository.findByUserIdAndProductId(userId, productId).isPresent()) {
            return "You have already liked this product.";
        }

        // Create and save the new like
        Like like = new Like();
        like.setProduct(product);
        like.setUser(user);
        like.setRating(1); // Set default rating or remove if unnecessary.
        likeRepository.save(like);

        return "Product liked successfully.";
    }

    @Override
    public String unLikeProduct(Long productId) {
        Long userId = loggedInUserUtil.getLoggedInUser().getId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Product not found"));

        // Find the like to delete
        Like like = likeRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new CustomException("You have not liked this product."));

        // Remove the like
        likeRepository.delete(like);
        return "Product unliked successfully.";
    }
}
