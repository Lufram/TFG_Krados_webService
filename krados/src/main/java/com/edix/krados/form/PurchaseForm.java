package com.edix.krados.form;

import lombok.Data;

import java.util.Date;

@Data
public class PurchaseForm {
    private Long id;
    private Date purchaseDate;
    private String status;
    private double totalPrice;
}
