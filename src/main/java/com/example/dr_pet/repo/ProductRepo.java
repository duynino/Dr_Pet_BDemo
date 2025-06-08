package com.example.dr_pet.repo;

import com.example.dr_pet.model.Category;
import com.example.dr_pet.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<Product> findById(long id);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory(Category category);



}
