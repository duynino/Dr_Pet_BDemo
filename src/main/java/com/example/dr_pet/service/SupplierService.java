package com.example.dr_pet.service;


import com.example.dr_pet.DTO.Request.SupplierRequest;
import com.example.dr_pet.DTO.Response.SupplierResponse;
import com.example.dr_pet.model.Supplier;
import com.example.dr_pet.repo.SupplierRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class SupplierService {
    @Autowired
    private SupplierRepo supplierRepo;

    private SupplierResponse mapToSupplierResponse(Supplier supplier){
        SupplierResponse dto = new SupplierResponse();
        dto.setSupplierID(supplier.getSupplierID());
        dto.setName(supplier.getName());
        dto.setDescription(supplier.getDescription());
        dto.setAddress(supplier.getAddress());
        dto.setPhoneNumber(supplier.getPhoneNumber());
        dto.setEmail(supplier.getEmail());
        dto.setCreatedDate(supplier.getCreatedDate());
        return dto;
    }

    public SupplierResponse addSupplier(SupplierRequest request){
        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setDescription(request.getDescription());
        supplier.setAddress(request.getAddress());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setEmail(request.getEmail());
        supplier.setActive(true);
        supplierRepo.save(supplier);
        return mapToSupplierResponse(supplier);
    }

    public List<SupplierResponse> getAllSuppliers(){
        List<Supplier> suppliers = supplierRepo.findAllByIsActiveTrue();
        return suppliers.stream().map(this::mapToSupplierResponse).collect(Collectors.toList());
    }

    public void deleteSupplier(Long supplierId){
        Supplier supplier = supplierRepo.findBySupplierIDAndIsActiveTrue(supplierId).orElseThrow(()->new RuntimeException("Supplier not found"));
        supplier.setActive(false);
        supplierRepo.save(supplier);
    }

    public SupplierResponse updateSupplier(Long supplierId, SupplierRequest request){
        Supplier supplier = supplierRepo.findBySupplierIDAndIsActiveTrue(supplierId).orElseThrow(()->new RuntimeException("Supplier not found"));
        supplier.setName(request.getName());
        supplier.setDescription(request.getDescription());
        supplier.setAddress(request.getAddress());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setEmail(request.getEmail());
        supplier.setUpdateDate(LocalDate.now());
        supplierRepo.save(supplier);
        return mapToSupplierResponse(supplier);
    }

    public SupplierResponse getSupplierDetail(Long id) {
        Supplier supplier = supplierRepo.findBySupplierIDAndIsActiveTrue(id).orElseThrow(()->new RuntimeException("Supplier not found"));
        return mapToSupplierResponse(supplier);
    }
}
