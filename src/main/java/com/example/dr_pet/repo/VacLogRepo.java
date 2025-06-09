package com.example.dr_pet.repo;

import com.example.dr_pet.model.Pet;
import com.example.dr_pet.model.VacLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacLogRepo extends JpaRepository<VacLog, Long >{
    Optional<VacLog> findVacLogByVacLogId(Long id);
    List<VacLog> findVacLogByPet(Pet pet);
    List<VacLog> findVacLogByPetAndIsActiveTrue(Pet pet);
    Optional<VacLog> findVacLogByVacLogIdAndIsActiveTrue(Long id);
}
