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
    private  final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final LoggedInUserUtil utils;
    private final LikeRepository likeRepository;

    public LikeServiceImpl(ProductRepository productRepository, UserRepository userRepository, LoggedInUserUtil utils, LikeRepository likeRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.utils = utils;
        this.likeRepository = likeRepository;
    }

    @Override
    public String likeProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException("Product not found"));
        int loggedInUserId = Math.toIntExact(utils.getLoggedInUser().getId());
        User user = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(user!=null){
            Like like = new Like();
            like.setId(user.getId());
            like.setProduct(product);
            like.setUser(user);
            like.setRating(1);
            likeRepository.save(like);
            product.getLikes().add(like);
            return "Product liked successfully";
        }
        return "Could not like the product";
    }

    @Override
    public String unLikeProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException("Product not found"));
        int loggedInUserId = Math.toIntExact(utils.getLoggedInUser().getId());
        User user = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(!product.getLikes().isEmpty()){
            for (Like like : product.getLikes()) {
                if(like.getId() ==user.getId() && like.getProduct().equals(product)){
                    Like likeToremove = product.getLikes().get(Math.toIntExact(user.getId()));
                    product.getLikes().remove(likeToremove);
                    likeRepository.delete(likeToremove);
                    return "Unliked successfully";
                }
                return "You cannot unlike another persons like";

            }


        }
        return "You cannot unlike because you never liked";
    }
}
