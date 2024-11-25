package com.alibou.security.controllers;

import lombok.Data;

@Data
public class AddItemRequest {
    private String size;
    private int quantity;
}
