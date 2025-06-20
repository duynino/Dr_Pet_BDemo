package com.example.dr_pet.DTO.Request;

import lombok.Data;

@Data
public class OrderRequest {
    private Long voucherID;
    private String note;
}