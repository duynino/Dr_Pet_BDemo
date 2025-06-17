package com.example.dr_pet.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {
    private String firstName;
    private String lastName;
    @NotBlank(message = "Phone number must not be empty")
    private String phoneNumber;
    @NotBlank(message = "Address must not be empty")
    private String address;
    @NotNull(message = "Birth date must not be empty")
    private Date birthDate;
}
