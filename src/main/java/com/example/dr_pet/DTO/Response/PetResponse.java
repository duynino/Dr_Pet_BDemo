package com.example.dr_pet.DTO.Response;

import com.example.dr_pet.model.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {
    private Long petID;
    private String name;
    private String description;
    private String gender;
    private Float weight;
    private LocalDate birthDate;
    private String species;
    private String breed;
    private Account account;
    private String image;
}
