package com.example.dr_pet.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productID;
    private String name;
    private String description;
    private String imageURl;
    private Float price;
    private Integer quantity;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "supplierID")
    @JsonBackReference
    private Supplier supplier;

    private boolean isActive;


}
