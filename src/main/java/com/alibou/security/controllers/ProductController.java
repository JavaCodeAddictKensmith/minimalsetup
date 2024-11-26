package com.alibou.security.controllers;

import com.alibou.security.dto.requests.CreateProductRequest;
import com.alibou.security.dto.responses.ProductSearchResponse;
import com.alibou.security.enums.Category;
import com.alibou.security.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addProduct")
    public String addProduct(@RequestBody CreateProductRequest productRequest) {
        return productService.addProduct(productRequest);

    }



    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getAllProducts")
    public String getAllProducts() {
        return productService.getAllProducts().toString();
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getProductById/{id}")
    public String getProductById(@PathVariable Long id) {

        return productService.getProductById(id).toString();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getProductByName/{name}")
    public String getProductByName(@PathVariable String name) {
        return productService.getProductByName(name).toString();
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getProductsByCategory/{category}")
    public ResponseEntity<String> getProductsByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category).toString());
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER`')")
    @GetMapping("/searchProductByKeyword/{keyword}")
    public ResponseEntity<List<ProductSearchResponse>> searchProductByKeyword(@PathVariable String keyword) {
        return ResponseEntity.ok(productService.getProductsByKeyword(keyword));
    }




}