package com.example.dr_pet.service;


import com.example.dr_pet.model.Account;
import com.example.dr_pet.model.PasswordResetToken;
import com.example.dr_pet.repo.AccountRepo;
import com.example.dr_pet.repo.PasswordResetTokenRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PasswordResetTokenRepo passwordResetTokenRepo;

    @Autowired
    private JavaMailSender mailSender;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Value("${app.front-end.url}")
    private String frontEndUrl;


    @Value("${app.reset-password.path:/reset-password}")
    private String resetPasswordPath;


    @Value("${app.reset-password.token.expiration-minutes:30}")
    private long tokenExpirationMinutes;

    @Transactional
    public void createPasswordResetTokenAndSendEmail(String email) {

        Account account = accountRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với email: " + email));
         passwordResetTokenRepo.deleteByAccount(account);

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(tokenExpirationMinutes);
        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(token);
        prt.setAccount(account);
        prt.setExpiryDate(expiryDate);
        passwordResetTokenRepo.save(prt);
        String resetLink = frontEndUrl + resetPasswordPath + "?token=" + token;

        sendEmailForPasswordReset(account.getEmail(), resetLink);
    }


    private void sendEmailForPasswordReset(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Dr_Pet: Yêu cầu đặt lại mật khẩu");
        message.setFrom("no-reply@drpet.com"); // hoặc cấu hình Spring Mail sender mặc định
        message.setText("Chào bạn,\n\n" +
                "Bạn nhận được email này vì chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.\n" +
                "Vui lòng nhấp vào đường link bên dưới để đặt lại mật khẩu. Đường link này chỉ có hiệu lực trong vòng "
                + tokenExpirationMinutes + " phút.\n\n" +
                resetLink + "\n\n" +
                "Nếu bạn không yêu cầu đặt lại mật khẩu, bạn có thể bỏ qua email này.\n\n" +
                "Trân trọng,\n" +
                "Đội ngũ Dr_Pet");
        mailSender.send(message);
    }

    public Account validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> prtOpt = passwordResetTokenRepo.findByToken(token);
        if (prtOpt.isEmpty()) {
            throw new RuntimeException("Token không hợp lệ");
        }
        PasswordResetToken prt = prtOpt.get();
        if (prt.getExpiryDate().isBefore(LocalDateTime.now())) {
            passwordResetTokenRepo.delete(prt);
            throw new RuntimeException("Token đã hết hạn");
        }
        return prt.getAccount();
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        Account account = validatePasswordResetToken(token);
        account.setPassword(bCryptPasswordEncoder.encode(newPassword));
        account.setUpdateTime(LocalDate.from(LocalDateTime.now()));
        accountRepo.save(account);
        passwordResetTokenRepo.deleteByAccount(account);
    }

}
