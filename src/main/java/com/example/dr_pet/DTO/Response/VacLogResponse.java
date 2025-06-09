package com.example.dr_pet.DTO.Response;

import com.example.dr_pet.model.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacLogResponse {
    private Long vacLogId;
    private String name;
    private String description;
    private String type;
    private LocalDate vacDate;
    private String notes;
    private Pet pet;
}
