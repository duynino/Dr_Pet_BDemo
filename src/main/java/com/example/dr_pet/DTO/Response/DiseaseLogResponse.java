package com.example.dr_pet.DTO.Response;


import com.example.dr_pet.model.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseLogResponse {
    private Long diseaseLogId;
    private String name;
    private String description;
    private LocalDate starDate;
    private LocalDate endDate;
    private Pet pet;
}
