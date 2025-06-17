package com.example.dr_pet.service;


import com.example.dr_pet.DTO.Request.DiseaseRequest;
import com.example.dr_pet.DTO.Response.DiseaseLogResponse;
import com.example.dr_pet.model.DiseaseLog;
import com.example.dr_pet.repo.DiseaseLogRepo;
import com.example.dr_pet.repo.PetRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiseaseLogService {

    @Autowired
    private DiseaseLogRepo diseaseLogRepo;

    @Autowired
    private PetRepo petRepo;

    private DiseaseLogResponse mapToDiseaseLogResponse(DiseaseLog diseaseLog){
        DiseaseLogResponse dto = new DiseaseLogResponse();
        dto.setDiseaseLogId(diseaseLog.getDiseaseLogId());
        dto.setDescription(diseaseLog.getDescription());
        dto.setPet(diseaseLog.getPet());
        dto.setName(diseaseLog.getName());
        dto.setEndDate(diseaseLog.getEndDate());
        dto.setStarDate(diseaseLog.getStarDate());
        return dto;
    }

    public List<DiseaseLogResponse> getAllDiseaseOfPet(Long petId){
        List<DiseaseLog> DiseaseList = diseaseLogRepo.findDiseaseLogByPetAndIsActiveTrue(petRepo.findById(petId).orElseThrow(()->new RuntimeException("Pet not found")));
        return DiseaseList.stream().map(this::mapToDiseaseLogResponse).collect(Collectors.toList());
    }

    public DiseaseLogResponse getDetailDiseaseOfPet(Long id){
        return mapToDiseaseLogResponse(diseaseLogRepo.findById(id).orElseThrow(()->new RuntimeException("Disease not found")));
    }

    public void addDisease(Long petId, DiseaseRequest request){
        DiseaseLog diseaseLog = new DiseaseLog();
        diseaseLog.setPet(petRepo.findByPetIDAndIsActiveTrue(petId).orElseThrow(()->new RuntimeException("Pet not found")));
        diseaseLog.setDescription(request.getDescription());
        diseaseLog.setName(request.getName());
        diseaseLog.setStarDate(request.getStarDate());
        diseaseLog.setEndDate(request.getEndDate());
        diseaseLog.setActive(true);
        diseaseLogRepo.save(diseaseLog);
    }



    public void deleteDisease(Long diseaseId){
        DiseaseLog diseaseLog = diseaseLogRepo.findByDiseaseLogIdAndIsActiveTrue(diseaseId).orElseThrow(() -> new RuntimeException("DiseaseLog not found"));
        diseaseLog.setActive(false);
        diseaseLogRepo.save(diseaseLog);
    }


    public DiseaseLogResponse updateDisease(Long diseaseId, DiseaseRequest request) {
        DiseaseLog diseaseLog = diseaseLogRepo.findByDiseaseLogIdAndIsActiveTrue(diseaseId)
                .orElseThrow(() -> new RuntimeException("Disease log not found"));
        diseaseLog.setName(request.getName());
        diseaseLog.setDescription(request.getDescription());
        diseaseLog.setStarDate(request.getStarDate());
        diseaseLog.setEndDate(request.getEndDate());
        diseaseLogRepo.save(diseaseLog);
        return mapToDiseaseLogResponse(diseaseLog);
    }
}
