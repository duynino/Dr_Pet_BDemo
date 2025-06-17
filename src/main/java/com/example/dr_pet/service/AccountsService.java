package com.example.dr_pet.service;


import com.example.dr_pet.DTO.Request.ChangePasswordRequest;
import com.example.dr_pet.DTO.Request.LoginRequest;
import com.example.dr_pet.DTO.Request.RegisterRequest;
import com.example.dr_pet.DTO.Request.UpdateAccountRequest;
import com.example.dr_pet.DTO.Response.AccountResponse;
import com.example.dr_pet.DTO.Response.LoginResponse;
import com.example.dr_pet.model.Account;
import com.example.dr_pet.model.Role;
import com.example.dr_pet.repo.AccountRepo;

import com.example.dr_pet.repo.RoleRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AccountsService {



    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AuthenticationManager authManager;

    public BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();



    //list all
    public List<AccountResponse> getAllAccounts(){
       List<Account> accounts = accountRepo.findAllByIsActiveTrue();
       return accounts.stream().map(this::mapToAccountResponse).toList();
    }


    //register method
    public AccountResponse registerAccount(RegisterRequest registerRequest) {
        if (accountRepo.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already in use");
        }
        if (accountRepo.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        Account account = new Account();
        account.setUsername(registerRequest.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        account.setEmail(registerRequest.getEmail());
        account.setIsActive(true);
        account.setCreateTime(LocalDate.now());
        Role defaultRole = roleRepo.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("No role found"));
        account.setRole(defaultRole);
        accountRepo.save(account);
        return mapToAccountResponse(account);
    }


    //login method
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception ex) {
            throw new RuntimeException("Sai username hoặc password");
        }

        // 2. Tạo token
        UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails.getUsername());

        Account account = accountRepo.findByUsernameAndIsActiveTrue(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        AccountResponse accountResp = mapToAccountResponse(account);

        return new LoginResponse(token, accountResp);
    }


    //changePassword method
    public void changePassword(String username, String oldPassword, String newPassword) {
        Account  recentAccount = accountRepo.findByUsernameAndIsActiveTrue(username).orElseThrow(() -> new RuntimeException("Account not found"));

        if (!bCryptPasswordEncoder.matches(oldPassword, recentAccount.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }
        recentAccount.setPassword(bCryptPasswordEncoder.encode(newPassword));
        recentAccount.setUpdateTime(LocalDate.now());
        accountRepo.save(recentAccount);
    }

    //getAccount method
    public AccountResponse getAccount(String username) {
        Account account = accountRepo.findByUsernameAndIsActiveTrue(username).orElseThrow(() -> new RuntimeException("Account not found"));
        return mapToAccountResponse(account);
    }

    //deleteAccount method
    public void deleteAccount(String username){
        Account account = accountRepo.findByUsernameAndIsActiveTrue(username).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setIsActive(false);
        accountRepo.save(account);
    }


    //updateAccount method
    public AccountResponse updateAccount(String username, UpdateAccountRequest   request){
        Account account = accountRepo.findByUsernameAndIsActiveTrue(username).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setPhoneNumber(request.getPhoneNumber());
        account.setAddress(request.getAddress());
        account.setUpdateTime(LocalDate.now());
        accountRepo.save(account);
        return mapToAccountResponse(account);
    }

    private AccountResponse mapToAccountResponse(Account account) {
        AccountResponse dto = new AccountResponse();
        dto.setAccountID(account.getAccountID());
        dto.setUsername(account.getUsername());
        dto.setEmail(account.getEmail());
        dto.setRoleName(account.getRole().getName());
        return dto;
    }






}
