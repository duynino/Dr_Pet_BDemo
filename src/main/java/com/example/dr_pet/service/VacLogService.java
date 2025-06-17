package com.example.dr_pet.service;


import com.example.dr_pet.DTO.Request.VacLogRequest;
import com.example.dr_pet.DTO.Response.VacLogResponse;
import com.example.dr_pet.model.Pet;
import com.example.dr_pet.model.VacLog;
import com.example.dr_pet.repo.PetRepo;
import com.example.dr_pet.repo.VacLogRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VacLogService {

    @Autowired
    private VacLogRepo vacLogRepo;

    @Autowired
    private PetRepo petRepo;

    private VacLogResponse mapToVacLogResponse(VacLog vacLog) {
        VacLogResponse dto = new VacLogResponse();
        dto.setVacLogId(vacLog.getVacLogId());
        dto.setPet(vacLog.getPet());
        dto.setName(vacLog.getName());
        dto.setType(vacLog.getType());
        dto.setVacDate(vacLog.getVacDate());
        dto.setNotes(vacLog.getNotes());
        dto.setDescription(vacLog.getDescription());
        return dto;
    }

    public VacLogResponse addVacLog(VacLogRequest request, Long petId) {
        VacLog vacLog = new VacLog();
        Pet pet = petRepo.findById(petId).orElseThrow(()-> new RuntimeException("Pet not found"));
        vacLog.setPet(pet);
        vacLog.setName(request.getName());
        vacLog.setType(request.getType());
        vacLog.setDescription(request.getDescription());
        vacLog.setVacDate(request.getVacDate());
        vacLog.setActive(true);
        vacLogRepo.save(vacLog);
        return mapToVacLogResponse(vacLog);
    }

    public List<VacLogResponse> getAllVacLogsByPet(Long petId) {
        List<VacLog> vacLogs = vacLogRepo.findVacLogByPetAndIsActiveTrue(petRepo.findById(petId).orElseThrow(()-> new RuntimeException("Pet not found")));
        return vacLogs.stream().map(this::mapToVacLogResponse).collect(Collectors.toList());
    }

    public void deleteVacLog(Long vacLogId) {
        VacLog vacLog = vacLogRepo.findVacLogByVacLogIdAndIsActiveTrue(vacLogId).orElseThrow(()-> new RuntimeException("VacLog not found"));
        vacLog.setActive(false);
        vacLogRepo.save(vacLog);
    }


    //update id
    public VacLogResponse updateVacLog(Long vacLogId, VacLogRequest request) {
        VacLog vacLog = vacLogRepo.findById(vacLogId)
                .orElseThrow(() -> new RuntimeException("VacLog not found"));
        vacLog.setName(request.getName());
        vacLog.setType(request.getType());
        vacLog.setDescription(request.getDescription());
        vacLog.setVacDate(request.getVacDate());
        vacLog.setUpdateDate(LocalDate.now());
        vacLogRepo.save(vacLog);
        return mapToVacLogResponse(vacLog);
    }

}
