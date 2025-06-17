package com.example.dr_pet.repo;

import com.example.dr_pet.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByAccountId(Long accountId);

}
