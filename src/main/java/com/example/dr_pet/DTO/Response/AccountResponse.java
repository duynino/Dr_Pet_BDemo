package com.example.dr_pet.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private Long accountID;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private String roleName;
    private Boolean isActive;
    private LocalDate createTime;
    private LocalDate updateTime;

}
