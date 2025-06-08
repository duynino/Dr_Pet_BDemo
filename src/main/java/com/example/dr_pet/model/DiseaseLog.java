package com.example.dr_pet.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diseaseLogId;
    private String name;
    private String description;
    private LocalDate starDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "petID")
    private Pet pet;

    private boolean isActive;


}
