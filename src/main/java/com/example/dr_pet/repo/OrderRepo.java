package com.example.dr_pet.repo;

import com.example.dr_pet.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {}