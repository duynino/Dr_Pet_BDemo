package com.example.dr_pet.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountID;

    @Column(unique = true, nullable = false)
    private String username;


    //Ignore password
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String address;
    private Date birthDate;
    private Boolean isActive;
    private LocalDate createTime;
    private LocalDate updateTime;

    @ManyToOne
    @JoinColumn(name = "roleID")
    @JsonBackReference
    private Role role;



}


