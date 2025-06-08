package com.example.dr_pet.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    @NotBlank
    private String username;
    @NotBlank(message = "Password must not be emtpy")
    private String newPassword;
    @NotBlank
    private String oldPassword;
}
