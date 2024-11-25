package com.alibou.security.dto.requests;

import com.alibou.security.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private Category category;

    private String imageUrl;


    private Long manufacturerId;



}
