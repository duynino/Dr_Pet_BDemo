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
public class DiseaseRequest {

    @NotBlank(message = "Name of disease must not be blank")
    private String name;
    private String description;
    @NotNull(message = "Start Date must not be null")
    private LocalDate starDate;
    private LocalDate endDate;
}
