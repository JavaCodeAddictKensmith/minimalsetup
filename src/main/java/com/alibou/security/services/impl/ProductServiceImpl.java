package com.alibou.security.services.impl;


import com.alibou.security.dto.requests.CreateProductRequest;
import com.alibou.security.dto.responses.ProductSearchResponse;
import com.alibou.security.enums.Category;
import com.alibou.security.exceptions.CustomException;
import com.alibou.security.exceptions.UserNotFoundException;
import com.alibou.security.models.Product;
import com.alibou.security.models.User;
import com.alibou.security.repositories.CartRepository;
import com.alibou.security.repositories.ProductRepository;
import com.alibou.security.repositories.UserRepository;
import com.alibou.security.services.ProductService;
import com.alibou.security.utility.LoggedInUserUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final LoggedInUserUtil utils;
    private final ProductRepository productRepository;

    private final CartRepository cartRepository;


    public ProductServiceImpl(UserRepository userRepository, LoggedInUserUtil utils, ProductRepository productRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.utils = utils;
        this.productRepository = productRepository;

        this.cartRepository = cartRepository;
    }

    @Override
    public String addProduct(CreateProductRequest productRequest) {
        String loggedInUser = utils.getLoggedInUser().getUsername();
        User user = userRepository.findByEmail(loggedInUser)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user != null) {

            Product product = Product.builder()
                    .price(productRequest.getPrice())
                    .productName(productRequest.getName())
                    .description(productRequest.getDescription())
                    .imageUrl(productRequest.getImageUrl())
                    .category(productRequest.getCategory())

                    .stock(productRequest.getStock())
                    .build();
            productRepository.save(product);

            return "product added successfully";
        }
        return "failed to add product";

    }




    @Override
    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("An error occurred while fetching products");
        }
    }


    @Override
    public Product getProductById(Long id) {

        return productRepository.findById(id).orElseThrow(() -> new CustomException("Product not found"));

    }

    @Override
    public Product updateProduct(Long id, CreateProductRequest productRequest) {
        return null;
    }


    @Override
    public String deleteProduct(Long id) {
        productRepository.deleteById(id);
        return "product deleted successfully";
    }

    @Override
    public Product getProductByName(String productName) {
        Optional<Product> product = productRepository.findByProductName(productName);
        if (product.isEmpty()) {
            throw new CustomException("Product not found");
        }
        return product.get();


    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        List<Product> products = productRepository.findByCategory(category);
        if (products.isEmpty()) {
            throw new CustomException("Product not found");
        }
        return products;
    }

    @Override
    public List<ProductSearchResponse> getProductsByKeyword(String keyword) {
        List<Product> products = productRepository.searchProductByKeyword(keyword);
        return products.stream()
                .map(ProductSearchResponse::new)
                .collect(Collectors.toList());
    }





}








