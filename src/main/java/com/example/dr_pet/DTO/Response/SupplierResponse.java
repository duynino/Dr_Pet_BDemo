package com.example.dr_pet.DTO.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse {
    private Long supplierID;
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String email;
    private LocalDate createdDate;
    private LocalDate updateDate;
}
