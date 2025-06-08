package com.example.dr_pet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petID;
    private String name;
    private String description;
    private String gender;
    private Float weight;
    private LocalDate birthDate;
    private LocalDate createdDate;
    private LocalDate updateDate;
    private String species;
    private String breed;

    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;

    private String image;


    private boolean isActive;
}
