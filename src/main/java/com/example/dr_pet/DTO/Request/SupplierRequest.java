package com.example.dr_pet.DTO.Request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequest {
    @NotBlank(message = "name must not be empty")
    private String name;
    private String description;
    @NotBlank(message = "address must not be empty")
    private String address;
    @NotBlank(message = "phone number must not be empty")
    private String phoneNumber;
    @NotBlank(message = "Email must not be empty")
    private String email;
}
