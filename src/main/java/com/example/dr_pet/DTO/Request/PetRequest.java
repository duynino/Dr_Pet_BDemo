package com.example.dr_pet.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {

    @NotBlank(message = "Please enter your pet name")
    private String name;
    private String description;
    @NotBlank(message = "Please enter pet gender")
    private String gender;
    @NotNull(message = "Weigh must not be empty")
    private Float weight;
    private LocalDate birthDate;
    private LocalDate createdDate;
    private LocalDate updateDate;
    private String species;
    private String breed;
    private String image;
}
