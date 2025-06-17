package com.example.dr_pet.service;

import com.example.dr_pet.model.Role;
import com.example.dr_pet.repo.RoleRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class RoleService {


    @Autowired
    private RoleRepo roleRepo;


    public Optional<Role> findByName(String name) {
        return roleRepo.findByName(name);
    }






}
