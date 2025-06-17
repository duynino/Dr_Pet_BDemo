package com.example.dr_pet.controller;


import com.example.dr_pet.DTO.Request.SupplierRequest;
import com.example.dr_pet.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    //list all
    @GetMapping
    public ResponseEntity<?> getAllSuppliers(){
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    //delete
    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id){
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    //add
    @PostMapping("add")
    public ResponseEntity<?> addSupplier(@Valid @RequestBody SupplierRequest supplierRequest, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(errMsg);
        }
        try{
            supplierService.addSupplier(supplierRequest);
            return ResponseEntity.ok("Add successfully");
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //update
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierRequest supplierRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(errMsg);
        }
        try{
            supplierService.updateSupplier(id, supplierRequest);
            return ResponseEntity.ok("Update successfully");
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    //detail
    @GetMapping("detail/{id}")
    public ResponseEntity<?> getSupplierDetail(@PathVariable Long id){
        return ResponseEntity.ok(supplierService.getSupplierDetail(id));
    }




}
