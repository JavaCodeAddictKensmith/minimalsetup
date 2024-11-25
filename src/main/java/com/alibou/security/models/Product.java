package com.alibou.security.models;
import com.alibou.security.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String description;
    private Double price;
    private Integer quantity;
    private Integer stock;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private Category category;
    @OneToMany(mappedBy = "product")
    private List<Review> reviews;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Like> likes;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", stock=" + stock +
                ", imageUrl='" + imageUrl + '\'' +
                ", category=" + category +
                ", reviews=" + reviews +
                ", likes=" + likes +
                '}';
    }



}
