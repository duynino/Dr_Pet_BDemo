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

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory(Category category);

    Optional<Product> findByProductIDAndIsActiveTrue(Long productID);
    // Tìm tất cả sản phẩm của một Category mà isActive = true
    List<Product> findByCategoryAndIsActiveTrue(Category category);

    List<Product> findAllByIsActiveTrue();



}
