package com.example.dr_pet.repo;

import com.example.dr_pet.model.DiseaseLog;
import com.example.dr_pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface DiseaseLogRepo extends JpaRepository< DiseaseLog, Long> {
    Optional<DiseaseLog> findByDiseaseLogId(Long diseaseLogId);
    List<DiseaseLog> findDiseaseLogByPet( Pet pet );
}
