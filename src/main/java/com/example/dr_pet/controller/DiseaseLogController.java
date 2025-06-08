package com.example.dr_pet.controller;


import com.example.dr_pet.DTO.Request.DiseaseRequest;
import com.example.dr_pet.DTO.Response.DiseaseLogResponse;
import com.example.dr_pet.service.DiseaseLogService;
import com.example.dr_pet.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disease")
public class DiseaseLogController {

    @Autowired
    private DiseaseLogService diseaseLogService;

    @Autowired
    private PetService petService;

    //get all disease for one pet
    @GetMapping("all/pet/{id}")
    public ResponseEntity<?> getDiseaseOfPet(@PathVariable Long id) {
        List<DiseaseLogResponse> list = diseaseLogService.getAllDiseaseOfPet(id);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Empty List");
        }
        return ResponseEntity.ok(list) ;
    }


    //get detail one disease
    @GetMapping("detail/{id}")
    public ResponseEntity<?> getDetailDisease(@PathVariable Long id) {
        DiseaseLogResponse diseaseLogResponse = diseaseLogService.getDetailDiseaseOfPet(id);
        if (diseaseLogResponse == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(diseaseLogResponse);
    }


    // add illness for pet
    @PostMapping("/add/pet/{id}")
    public ResponseEntity<?> addDiseaseOfPet(@Valid  @RequestBody DiseaseRequest diseaseRequest, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(errMsg);
        }
        try{
            diseaseLogService.addDisease(id,diseaseRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Add success");

        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


    // delete one illness
    @DeleteMapping("delete/pet/{id}")
    public ResponseEntity<?> deleteDiseaseOfPet(@PathVariable Long id) {
        diseaseLogService.deleteDisease(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete success");
    }



    @PutMapping("update/pet/{diseaseId}")
    public ResponseEntity<?> updateDisease(@PathVariable Long diseaseId,
                                           @Valid @RequestBody DiseaseRequest request,
                                           BindingResult result) {
        if (result.hasErrors()) {
            String msg = result.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity.badRequest().body(msg);
        }

        DiseaseLogResponse updated = diseaseLogService.updateDisease(diseaseId, request);
        return ResponseEntity.ok(updated);
    }




















}
