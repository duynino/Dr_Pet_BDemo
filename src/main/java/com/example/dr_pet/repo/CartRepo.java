package com.example.dr_pet.repo;

import com.example.dr_pet.model.Account;
import com.example.dr_pet.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByAccount(Account account);

}
