package com.example.dr_pet.repo;

import com.example.dr_pet.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long  > {
    Optional<Category> findByName(String name);
    Optional<Category> findById(Long id);

    Optional<Category> findByCategoryIDAndIsActiveTrue(Long categoryID);

    List<Category> findAllByIsActiveTrue();
}
