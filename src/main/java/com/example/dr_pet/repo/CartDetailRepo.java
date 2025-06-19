package com.example.dr_pet.repo;

import com.example.dr_pet.model.Cart;
import com.example.dr_pet.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepo extends JpaRepository<CartDetail, Long> {

    List<CartDetail> findCartDetailByCart(Cart cart);



}
