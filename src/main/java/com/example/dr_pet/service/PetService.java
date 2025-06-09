package com.example.dr_pet.service;

import com.example.dr_pet.DTO.Request.PetRequest;
import com.example.dr_pet.DTO.Response.PetResponse;
import com.example.dr_pet.model.Account;
import com.example.dr_pet.model.Pet;
import com.example.dr_pet.repo.AccountRepo;
import com.example.dr_pet.repo.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {


    @Autowired
    private PetRepo petRepo;

    @Autowired
    private AccountRepo accountRepo;

    private PetResponse mapToPetResponse(Pet pet) {
        PetResponse dto = new PetResponse();
        dto.setPetID(pet.getPetID());
        dto.setName(pet.getName());
        dto.setGender(pet.getGender());
        dto.setBirthDate(pet.getBirthDate());
        dto.setWeight(pet.getWeight());
        dto.setDescription(pet.getDescription());
        dto.setBreed(pet.getBreed());
        dto.setSpecies(pet.getSpecies());
        dto.setImage(pet.getImage());
        return dto;
    }

    //add pet
    public PetResponse addPet(String username,PetRequest petRequest) {
        Account owner = accountRepo.findByUsername(username).orElseThrow(()->new RuntimeException("Account not found"));
        Pet pet = new Pet();
        pet.setAccount(owner);
        pet.setName(petRequest.getName());
        pet.setGender(petRequest.getGender());
        pet.setBirthDate(petRequest.getBirthDate());
        pet.setWeight(petRequest.getWeight());
        pet.setDescription(petRequest.getDescription());
        pet.setSpecies(petRequest.getSpecies());
        pet.setBreed(petRequest.getBreed());
        pet.setImage(petRequest.getImage());
        pet.setActive(true);
        petRepo.save(pet);
        return mapToPetResponse(pet);
    }

    public PetResponse getPet(Long petID) {
        Pet pet = petRepo.findByPetIDAndIsActiveTrue(petID).orElseThrow(()->new RuntimeException("Pet not found"));
        return mapToPetResponse(pet);
    }

    //list all pet in account
    public List<PetResponse> getAllPetsByAccount(String username) {
        Account owner = accountRepo.findByUsername(username).orElseThrow(()->new RuntimeException("Account not found"));
        List<Pet> pets = petRepo.findByAccountAndIsActiveTrue(owner);
        return pets.stream().map(this::mapToPetResponse).collect(Collectors.toList());
    }


    //update Pet
    public PetResponse updatePet(Long petID,PetRequest petRequest) {
        Pet pet = petRepo.findByPetIDAndIsActiveTrue(petID).orElseThrow(()->new RuntimeException("Pet not found"));
        pet.setName(petRequest.getName());
        pet.setGender(petRequest.getGender());
        pet.setBirthDate(petRequest.getBirthDate());
        pet.setWeight(petRequest.getWeight());
        pet.setDescription(petRequest.getDescription());
        pet.setSpecies(petRequest.getSpecies());
        pet.setImage(petRequest.getImage());
        pet.setUpdateDate(LocalDate.now());
        petRepo.save(pet);
        return mapToPetResponse(pet);
    }

    //delete pet by id
    public void deletePet(Long petID) {
        Pet pet = petRepo.findByPetIDAndIsActiveTrue(petID).orElseThrow(()->new RuntimeException("Pet not found"));
        pet.setActive(false);
    }






















}
