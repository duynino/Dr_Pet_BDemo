package com.example.dr_pet.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Voucher_user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucher_user_id;

    @ManyToOne @JoinColumn(name = "voucherID")
    @JsonBackReference
    private Voucher voucher;

    @ManyToOne @JoinColumn(name = "accountID")
    @JsonBackReference
    private Account account;

}
