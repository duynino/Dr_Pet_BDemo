package com.example.dr_pet.controller;


import com.example.dr_pet.DTO.Request.*;
import com.example.dr_pet.DTO.Response.AccountResponse;
import com.example.dr_pet.DTO.Response.LoginResponse;
import com.example.dr_pet.model.Account;
import com.example.dr_pet.model.UserPrincipal;
import com.example.dr_pet.service.AccountsService;
import com.example.dr_pet.service.PasswordResetTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/auth/")
@RestController
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private PasswordResetTokenService  passwordResetTokenService;



    //register method
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest registerRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity.badRequest().body(errorMsg);
        }

        try {
            AccountResponse savedAcc = accountsService.registerAccount(registerRequest);
            return ResponseEntity.ok(savedAcc);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }



    //login method
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu login không hợp lệ");
            return ResponseEntity.badRequest().body(errorMsg);
        }

        try {
            LoginResponse resp = accountsService.login(loginRequest);
            return ResponseEntity.ok(resp);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(401).body(ex.getMessage());
        }
    }



    //change password in the user login
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody ChangePasswordRequest changePasswordRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Du lieu Khong hop le");
            return ResponseEntity.badRequest().body(errorMsg);
        }
        String username = userPrincipal.getUsername();
        try {
            accountsService.changePassword(username, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());

            return ResponseEntity.ok("Đổi mật khẩu thành công");
        } catch (RuntimeException ex) {
            String msg = ex.getMessage();
            if (msg.equals("User không tồn tại")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            } else if (msg.equals("Mật khẩu cũ không đúng")) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(msg);
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(msg);
            }
        }
    }


    @PostMapping("/forgotPassword")
    public ResponseEntity<?> fogotPassword(@Valid @RequestBody ForgotPassworđRequest fogotPasswordRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errMsg);
        }
        try {
            passwordResetTokenService.createPasswordResetTokenAndSendEmail(fogotPasswordRequest.getEmail());
            return ResponseEntity.ok("Đã gửi email đặt lại mật khẩu (nếu email tồn tại trong hệ thống).");
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }

    }

    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(
            @RequestParam("token") String token,                                 // <-- đọc token từ query param
            @Valid @RequestBody ResetPasswordRequest resetRequest,               // <-- chỉ chứa newPassword
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errMsg);
        }

        try {
            passwordResetTokenService.resetPassword(
                    token,
                    resetRequest.getNewPassword()
            );
            return ResponseEntity.ok("Đổi mật khẩu thành công");
        } catch (RuntimeException ex) {
            String msg = ex.getMessage();
            if (msg.contains("Token không hợp lệ") || msg.contains("Token đã hết hạn")) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(msg);
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
        }
    }

}














