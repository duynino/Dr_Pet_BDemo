package com.example.dr_pet.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("ALL")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DetailOrders")
public class DetailOrder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long detailOrderID;


    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private Order order;

    private int quantity;
    private Float price;


}
