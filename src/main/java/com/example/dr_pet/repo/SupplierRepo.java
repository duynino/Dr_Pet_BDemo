package com.example.dr_pet.repo;

import com.example.dr_pet.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findBySupplierIDAndIsActiveTrue(Long supplierID);

    List<Supplier> findAllByIsActiveTrue();

    Optional<Supplier> findByName(String name);

}
