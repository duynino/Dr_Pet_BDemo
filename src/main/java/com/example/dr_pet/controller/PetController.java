package com.example.dr_pet.controller;


import com.example.dr_pet.DTO.Request.PetRequest;
import com.example.dr_pet.DTO.Response.PetResponse;
import com.example.dr_pet.model.UserPrincipal;
import com.example.dr_pet.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;


    //Add Pet
    @PostMapping("addPet")
    public ResponseEntity<?> createPet(@AuthenticationPrincipal UserPrincipal userPrincipal ,
                                                 @Valid @RequestBody PetRequest petRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(errMsg);
        }

        try {
            PetResponse resp = petService.addPet(userPrincipal.getUsername(), petRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Add pet ok");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPet(@PathVariable Long id) {
        PetResponse petResponse = petService.getPet(id);
        return ResponseEntity.ok(petResponse);
    }



    //Get Pet in account
    @GetMapping("all")
    public ResponseEntity<?> getPet(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<PetResponse> listPet = petService.getAllPetsByAccount(userPrincipal.getUsername());
        if (listPet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found any pet in account");
        }
        return ResponseEntity.ok(listPet);
    }


    //update
    @PutMapping("update/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Long id, @Valid @RequestBody PetRequest petRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(errMsg);
        }
        try {
            PetResponse updated = petService.updatePet(id, petRequest);
            return ResponseEntity.ok("Update successful");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Update failed");
        }
    }


    //delete pet
    @PutMapping("delete/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete pet ok");
    }






}


