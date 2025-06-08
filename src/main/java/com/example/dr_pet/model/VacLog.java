package com.example.dr_pet.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacLogId;
    private String name;
    private String description;
    private String type;
    private String notes;
    private LocalDate updateDate;
    private boolean isActive;

    @NotNull(message = "Vacinated Date must not be null")
    private LocalDate vacDate;


    @ManyToOne
    @JoinColumn(name = "petID")
    private Pet pet;

}
