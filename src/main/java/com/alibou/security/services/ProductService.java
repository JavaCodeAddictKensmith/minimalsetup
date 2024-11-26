package com.alibou.security.services;

import com.alibou.security.dto.requests.CreateProductRequest;
import com.alibou.security.dto.responses.ProductSearchResponse;
import com.alibou.security.enums.Category;
import com.alibou.security.models.Product;

import java.util.List;

public interface ProductService {
    String addProduct(CreateProductRequest productRequest);

    List<Product> getAllProducts();

    Product getProductById(Long id);
    String updateProduct(Long id,CreateProductRequest productRequest);

    String deleteProduct(Long id);


    Product getProductByName(String name);

    List<Product> getProductsByCategory(Category category);

    List<ProductSearchResponse> getProductsByKeyword(String keyword);






}