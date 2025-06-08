package com.example.dr_pet.repo;

import com.example.dr_pet.model.Account;
import com.example.dr_pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepo extends JpaRepository<Pet, Long> {
    Optional<Pet> findById(Long id);
    Optional<Pet> findByName(String name);
    List<Pet> findByNameContaining(String name);
    List<Pet> findByAccount(Account account);
}
