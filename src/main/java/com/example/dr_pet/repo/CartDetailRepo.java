package com.example.dr_pet.repo;

import com.example.dr_pet.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDetailRepo extends JpaRepository<CartDetail, Long> {

    List<CartDetail> findByCart(Long cartId);



}
