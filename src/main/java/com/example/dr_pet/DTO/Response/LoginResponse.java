package com.example.dr_pet.DTO.Response;

import com.example.dr_pet.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private AccountResponse accountResponse;
}



