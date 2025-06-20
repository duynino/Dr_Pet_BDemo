package com.example.dr_pet.repo;

import com.example.dr_pet.model.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailOrderRepo extends JpaRepository<DetailOrder, Long> {}