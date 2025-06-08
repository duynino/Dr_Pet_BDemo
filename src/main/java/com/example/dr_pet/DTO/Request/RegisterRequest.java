package com.example.dr_pet.DTO.Request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "User must not be empty")
    @Size(min = 4, max = 20, message = "Username must be 4-20 character")
    private String username;


    @NotBlank(message = "Password must not be empty")
    @Size(min = 6,message = "Password must be 6 character")
    private String password;

    @NotBlank(message = "Email must not be empty")
    private String email;



    private LocalDate createTime;
    private LocalDate updateTime;


}
