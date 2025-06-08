package com.example.dr_pet.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacLogRequest {

    @NotBlank(message = "name must not be empty")
    private String name;
    private String description;
    private String type;
    @NotNull(message = "Vacinated Date must be fill")
    private LocalDate vacDate;


}
