package com.alibou.security.dto.responses;

import com.alibou.security.dto.requests.CartItemDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponseDto {
    private double subTotal;
    private int totalQuantity;
    private List<CartItemDto> cartItem;



}