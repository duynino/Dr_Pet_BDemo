package com.example.dr_pet.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderID;

    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "voucherID")
    private Voucher voucher;

    private Float totalPrice;
    private Float discountPrice;
    private String orderStatus;
    private LocalDate createDate;
    private LocalDate updateDate;
    private boolean isActive;
    private String note;
}
