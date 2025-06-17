package com.example.dr_pet.repo;

import com.example.dr_pet.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

   Optional<Account> findByUsernameAndIsActiveTrue(String username);

   boolean existsByUsername(String username);

   Optional<Account> findByEmail(String email);

   List<Account> findAllByIsActiveTrue();

   boolean existsByEmail(String email);

}
