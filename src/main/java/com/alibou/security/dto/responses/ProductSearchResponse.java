package com.alibou.security.dto.responses;

import com.alibou.security.enums.Category;
import com.alibou.security.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchResponse {
    private Long id;

    private String productName;

    private String description;

    private Double price;
    private Category category;
    private String image;


    public ProductSearchResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.image = product.getImageUrl();

    }
}
