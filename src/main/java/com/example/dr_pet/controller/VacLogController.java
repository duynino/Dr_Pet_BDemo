package com.example.dr_pet.controller;


import com.example.dr_pet.DTO.Request.VacLogRequest;
import com.example.dr_pet.DTO.Response.PetResponse;
import com.example.dr_pet.DTO.Response.VacLogResponse;
import com.example.dr_pet.model.Pet;
import com.example.dr_pet.model.VacLog;
import com.example.dr_pet.service.PetService;
import com.example.dr_pet.service.VacLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/vac")
@RestController
public class VacLogController {

    @Autowired
    private VacLogService vacLogService;

    @Autowired
    private PetService petService;


    //add vac log
    @PostMapping("add/pet/{id}")
    public ResponseEntity<?> addVacLog(@Valid @RequestBody VacLogRequest vacLogRequest,
                                       BindingResult bindingResult,
                                       @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(errMsg);
        }
        try{
            VacLogResponse vacLogResponse = vacLogService.addVacLog(vacLogRequest,id);
            return ResponseEntity.status(HttpStatus.CREATED).body(vacLogResponse);
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Create fail");
        }
    }


   // get all  vacLog
    @GetMapping("pet/{id}")
    public ResponseEntity<?> getVacLog(@PathVariable Long id) {
        List<VacLogResponse> listVac = vacLogService.getAllVacLogsByPet(id);
        return ResponseEntity.status(HttpStatus.OK).body(listVac);
    }


    //Delete
    @PutMapping("/delete/{vacLogId}")
    public ResponseEntity<?> deleteVacLog(@PathVariable Long vacLogId) {
        try {
            vacLogService.deleteVacLog(vacLogId);
            return ResponseEntity.ok("Deleted successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


    //update
    @PutMapping("/update/{vacLogId}")
    public ResponseEntity<?> updateVacLog(@PathVariable Long vacLogId,
                                          @RequestBody VacLogRequest request) {
        try {
            VacLogResponse updated = vacLogService.updateVacLog(vacLogId, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}
