package com.alibou.security.controllers;

import com.alibou.security.services.CartService;
import com.alibou.security.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {
    private final LikeService likeService;

//    public LikeController(LikeService likeService) {
//        this.likeService = likeService;
//    }

    @PostMapping("/{productId}/like")
    public ResponseEntity<String> likeProduct(@PathVariable Long productId) {
        String response = likeService.likeProduct(productId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}/unlike")
    public ResponseEntity<String> unLikeProduct(@PathVariable Long productId) {
        String response = likeService.unLikeProduct(productId);
        return ResponseEntity.ok(response);
    }

}