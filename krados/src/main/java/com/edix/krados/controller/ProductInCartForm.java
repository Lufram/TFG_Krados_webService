package com.edix.krados.controller;

import lombok.Data;

@Data
public class ProductInCartForm {

    private Long id;
    private String name;
    private String info;
    private double uPrice;
    private int amount;

}
